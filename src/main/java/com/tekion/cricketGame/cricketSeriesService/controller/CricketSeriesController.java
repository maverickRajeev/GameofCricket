package com.tekion.cricketGame.cricketSeriesService.controller;

import com.tekion.cricketGame.cricketSeriesService.CricketSeriesService;
import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import com.tekion.cricketGame.cricketSeriesService.dto.SeriesRequestDto;
import com.tekion.cricketGame.utils.MathCalculations;
import com.tekion.cricketGame.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
@RequestMapping("/series")
public class CricketSeriesController {

    @Autowired
    private CricketSeriesService cricketSeriesService;

    @PostMapping(value = "/newSeries" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<CricketSeriesBean> startSeries(@RequestBody SeriesRequestDto newSeries) {
        CricketSeriesBean createdSeries;
        if(RequestValidator.seriesRequestValidator(newSeries)) {
            createdSeries = cricketSeriesService.beginSeries(newSeries);
            return ResponseEntity.ok().body(createdSeries);
        }else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(value = "/bulkNewSeries/{count}" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody Map<String , Double> bulkCallNewSeries(@RequestBody SeriesRequestDto newSeries , @PathVariable("count") int threadCount){
        HashMap<String , Double > metricsDetails = new HashMap<>();
        if(!RequestValidator.seriesRequestValidator(newSeries))
            return metricsDetails;

        long[] responseTimes = new long[threadCount];
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0 , currentTime = 0;
        for(int i = 0 ; i < threadCount ; i++){
            startSeries(newSeries);
            currentTime = System.currentTimeMillis() - startTime;
            responseTimes[i] = currentTime - elapsedTime;
            elapsedTime = currentTime;
        }
        long endTime = System.currentTimeMillis();
        long totalResponseTime = endTime - startTime;

        metricsDetails.put("avgResponseTime" , (double)totalResponseTime/(double) threadCount);
        metricsDetails.put("90thPercentileTime" , MathCalculations.PercentileCalculation(responseTimes , 90));
        metricsDetails.put("99thPercentileTime" , MathCalculations.PercentileCalculation(responseTimes , 99));

        return metricsDetails;
    }

    @GetMapping(value = "/seriesDetails/{id}")
    public @ResponseBody ResponseEntity<CricketSeriesBean> getSeriesDetails(@PathVariable("id") int seriesId){
        CricketSeriesBean seriesInfo;
        if(cricketSeriesService.checkIfSeriesExists(seriesId)) {
            seriesInfo = cricketSeriesService.getSeriesDetails(seriesId);
            return ResponseEntity.ok().body(seriesInfo);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "/bulkSeriesDetails/{id}/{count}")
    public @ResponseBody Map<String , Double> bulkCallGetSeriesDetails(@PathVariable("id") int seriesId , @PathVariable("count") int taskCount ){
        HashMap<String , Double > metricsDetails = new HashMap<>();
        if(!cricketSeriesService.checkIfSeriesExists(seriesId))
            return metricsDetails;

        long[] responseTimes = new long[taskCount];
        long totalResponseTime = 0;
        //100

        ExecutorService service = Executors.newFixedThreadPool(100);

        // Spring microservices at action --
        // at any time you cannot spawn more than 10 threads
        // semaphore --> to control the number of threads or tasks submitted to the cacheThreadPool
        for(int i = 0 ; i < taskCount ; i++){
            long startTaskTime = System.currentTimeMillis();
            service.execute(new Task(seriesId));
            long endTaskTime = System.currentTimeMillis();
            responseTimes[i] = endTaskTime - startTaskTime;
            totalResponseTime += responseTimes[i];
        }

        for(int i = 0 ; i < responseTimes.length ; i++){
            System.out.println(responseTimes[i]);
        }

        metricsDetails.put("avgResponseTime" , (double)totalResponseTime/(double) taskCount);
        metricsDetails.put("90thPercentileTime" , MathCalculations.PercentileCalculation(responseTimes , 90));
        metricsDetails.put("99thPercentileTime" , MathCalculations.PercentileCalculation(responseTimes , 99));

        return metricsDetails;
    }

    private class Task implements Runnable{
        private final int seriesId;
        public Task(int seriesId) {
            this.seriesId = seriesId;
        }

        @Override
        public void run() {
            System.currentTimeMillis();

            getSeriesDetails(seriesId);
        }
    }
}

