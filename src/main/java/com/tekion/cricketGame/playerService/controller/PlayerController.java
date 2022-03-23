package com.tekion.cricketGame.playerService.controller;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.playerService.PlayerService;
import com.tekion.cricketGame.playerService.bean.PlayerBean;
import com.tekion.cricketGame.playerService.bean.PlayerStatsBean;
import com.tekion.cricketGame.utils.MathCalculations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CricketMatchService cricketMatchService;

    @GetMapping("/playerInfo/{id}")
    public @ResponseBody ResponseEntity<PlayerBean> getPlayerInfo(@PathVariable("id") int playerId){
        PlayerBean playerInfo = new PlayerBean();
        if(playerService.checkIfPlayerIdExists(playerId)){
            playerInfo = playerService.getPlayerDetails(playerId);
            return ResponseEntity.ok().body(playerInfo);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/bulkPlayerInfo/{id}/{count}")
    public @ResponseBody Map<String , Double> bulkCallPlayerInfo(@PathVariable("id") int playerId , @PathVariable("count") int threadCount ){
        HashMap<String , Double > metricsDetails = new HashMap<>();
        if(!playerService.checkIfPlayerIdExists(playerId))
            return metricsDetails;

        long[] responseTimes = new long[threadCount];
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0 , currentTime = 0;
        for(int i = 0 ; i < threadCount ; i++){
            getPlayerInfo(playerId);
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

    @GetMapping("/playerStat/{playerId}/{matchId}")
    public @ResponseBody ResponseEntity<PlayerStatsBean> getPlayerStats(@PathVariable("playerId") int playerId , @PathVariable("matchId") int matchId){
        PlayerStatsBean playerStat;
        if(playerService.checkIfPlayerIdExists(playerId) && cricketMatchService.checkIfMatchExists(matchId)){
            playerStat = playerService.getPlayerStats(playerId , matchId);
            return ResponseEntity.ok().body(playerStat);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bulkPlayerStats/{playerId}/{matchId}/{count}")
    public @ResponseBody Map<String , Double> bulkCallGetMatchList(@PathVariable("playerId") int playerId ,  @PathVariable("matchId") int matchId , @PathVariable("count") int threadCount ){
        HashMap<String , Double > metricsDetails = new HashMap<>();
        if(!playerService.checkIfPlayerIdExists(playerId) && cricketMatchService.checkIfMatchExists(matchId))
            return metricsDetails;

        long[] responseTimes = new long[threadCount];
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0 , currentTime = 0;
        for(int i = 0 ; i < threadCount ; i++){
            getPlayerStats(playerId , matchId);
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
