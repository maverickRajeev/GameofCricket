package com.tekion.cricketGame.cricketSeriesService.controller;

import com.tekion.cricketGame.cricketSeriesService.CricketSeriesService;
import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import com.tekion.cricketGame.cricketSeriesService.dto.SeriesRequestDto;
import com.tekion.cricketGame.utils.PerfTestDetails;
import com.tekion.cricketGame.utils.TaskLimitSemaphore;
import com.tekion.cricketGame.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

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
    public @ResponseBody Map<String , Double> bulkCallNewSeries(@RequestBody SeriesRequestDto newSeries , @PathVariable("count") int taskCount){
        HashMap<String , Double > metricsDetails = new HashMap<>();
        if(!RequestValidator.seriesRequestValidator(newSeries))
            return metricsDetails;

        long[] responseTimes = new long[taskCount];
        long totalResponseTime = 0;

        ExecutorService service = Executors.newFixedThreadPool(100);
        TaskLimitSemaphore taskLimitSemaphore = new TaskLimitSemaphore(service , 10);

        for(int i = 0 ; i < taskCount ; i++){
            Future<Long> future = null;
            try {
                future = taskLimitSemaphore.submit(new CreateNewSeriesTask(newSeries));
                responseTimes[i] = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            totalResponseTime += responseTimes[i];
        }

        return PerfTestDetails.getPerfMetricDetails(metricsDetails , taskCount , responseTimes , totalResponseTime);
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
    public @ResponseBody Map<String , Double> bulkCallGetSeriesDetails(@PathVariable("id") int seriesId , @PathVariable("count") int taskCount ) {
        HashMap<String , Double > metricsDetails = new HashMap<>();
        if(!cricketSeriesService.checkIfSeriesExists(seriesId))
            return metricsDetails;

        long[] responseTimes = new long[taskCount];
        long totalResponseTime = 0;

        ExecutorService service = Executors.newFixedThreadPool(100);
        TaskLimitSemaphore taskLimitSemaphore = new TaskLimitSemaphore(service , 10);

        for(int i = 0 ; i < taskCount ; i++){
            Future<Long> future = null;
            try {
                future = taskLimitSemaphore.submit(new GetSeriesDetailsTask(seriesId));
                responseTimes[i] = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            totalResponseTime += responseTimes[i];
        }

        return PerfTestDetails.getPerfMetricDetails(metricsDetails , taskCount , responseTimes , totalResponseTime);
    }

    private class CreateNewSeriesTask implements Callable<Long> {

        private final SeriesRequestDto newSeries;

        public CreateNewSeriesTask(SeriesRequestDto newSeries){
            this.newSeries = newSeries;
        }

        @Override
        public Long call() throws Exception{
           long startTime = System.currentTimeMillis();
           startSeries(this.newSeries);
           long endTime = System.currentTimeMillis();
           return endTime - startTime;
        }

    }

    private class GetSeriesDetailsTask implements Callable<Long> {
        private final int seriesId;

        public GetSeriesDetailsTask(int seriesId) {
            this.seriesId = seriesId;
        }

        @Override
        public Long call() throws Exception {
            long startTime =  System.currentTimeMillis();
            getSeriesDetails(seriesId);
            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }

}

