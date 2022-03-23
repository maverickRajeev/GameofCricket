package com.tekion.cricketGame.cricketMatchService.controller;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.cricketMatchService.bean.CricketMatchBean;
import com.tekion.cricketGame.cricketSeriesService.CricketSeriesService;
import com.tekion.cricketGame.utils.MathCalculations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/match")
public class CricketMatchController {

    @Autowired
    private CricketMatchService cricketMatchService;

    @Autowired
    private CricketSeriesService cricketSeriesService;

    @GetMapping("/matchDetails/{id}")
    public @ResponseBody ResponseEntity<CricketMatchBean> getMatchDetails(@PathVariable("id") int matchId){
        CricketMatchBean matchInfo;
        if(cricketMatchService.checkIfMatchExists(matchId)) {
            matchInfo = cricketMatchService.getMatchDetails(matchId);
            return ResponseEntity.ok().body(matchInfo);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bulkMatchDetails/{id}/{count}")
    public @ResponseBody Map<String , Double> bulkCallGetMatchDetails(@PathVariable("id") int matchId , @PathVariable("count") int threadCount ){
        HashMap<String , Double > metricsDetails = new HashMap<>();
        if(!cricketMatchService.checkIfMatchExists(matchId))
            return metricsDetails;

        long[] responseTimes = new long[threadCount];
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0 , currentTime = 0;
        for(int i = 0 ; i < threadCount ; i++){
            getMatchDetails(matchId);
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

    @GetMapping("/matchList/{id}")
    public @ResponseBody ResponseEntity<List<CricketMatchBean>> getAllMatchesBySeriesId(@PathVariable("id") int seriesId){
        List<CricketMatchBean> matchList;
        if(cricketSeriesService.checkIfSeriesExists(seriesId)){
            matchList = cricketMatchService.listAllMatchesBySeriesId(seriesId);
            return ResponseEntity.ok().body(matchList);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bulkMatchList/{id}/{count}")
    public @ResponseBody Map<String , Double> bulkCallGetMatchList(@PathVariable("id") int seriesId , @PathVariable("count") int threadCount ){
        HashMap<String , Double > metricsDetails = new HashMap<>();
        if(!cricketSeriesService.checkIfSeriesExists(seriesId))
            return metricsDetails;

        long[] responseTimes = new long[threadCount];
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0 , currentTime = 0;
        for(int i = 0 ; i < threadCount ; i++){
           getAllMatchesBySeriesId(seriesId);
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
}
