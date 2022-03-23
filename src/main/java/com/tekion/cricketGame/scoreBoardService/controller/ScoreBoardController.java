package com.tekion.cricketGame.scoreBoardService.controller;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.scoreBoardService.ScoreBoardService;
import com.tekion.cricketGame.scoreBoardService.bean.MatchScoreBoardBean;
import com.tekion.cricketGame.utils.MathCalculations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/scoreBoard")
public class ScoreBoardController {

    @Autowired
    private ScoreBoardService scoreBoardService;

    @Autowired
    private CricketMatchService cricketMatchService;

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<MatchScoreBoardBean> fetchScoreBoardDetails(@PathVariable("id") int matchId){
         MatchScoreBoardBean scoreBoardInfo;
         if(cricketMatchService.checkIfMatchExists(matchId)){
             scoreBoardInfo = scoreBoardService.getScoreBoardDetails(matchId);
             return ResponseEntity.ok().body(scoreBoardInfo);
         }else{
             return ResponseEntity.notFound().build();
         }
    }

    @GetMapping("/bulkScoreBoardDetails/{id}/{count}")
    public @ResponseBody Map<String , Double> bulkCallGetScoreboard(@PathVariable("id") int matchId , @PathVariable("count") int threadCount ){
        HashMap<String , Double > metricsDetails = new HashMap<>();
        if(!cricketMatchService.checkIfMatchExists(matchId))
            return metricsDetails;

        long[] responseTimes = new long[threadCount];
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0 , currentTime = 0;
        for(int i = 0 ; i < threadCount ; i++){
            fetchScoreBoardDetails(matchId);
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
