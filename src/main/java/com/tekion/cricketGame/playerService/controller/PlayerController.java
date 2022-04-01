package com.tekion.cricketGame.playerService.controller;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.playerService.PlayerService;
import com.tekion.cricketGame.playerService.bean.PlayerBean;
import com.tekion.cricketGame.playerService.bean.PlayerStatsBean;
import com.tekion.cricketGame.utils.PerfTestDetails;
import com.tekion.cricketGame.utils.TaskLimitSemaphore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CricketMatchService cricketMatchService;

    @GetMapping("/playerInfo/{id}")
    public @ResponseBody ResponseEntity<PlayerBean> getPlayerInfo(@PathVariable("id") int playerId){
        PlayerBean playerInfo;
        if(playerService.checkIfPlayerIdExists(playerId)){
            playerInfo = playerService.getPlayerDetails(playerId);
            return ResponseEntity.ok().body(playerInfo);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/bulkPlayerInfo/{id}/{count}")
    public @ResponseBody Map<String , Double> bulkCallPlayerInfo(@PathVariable("id") int playerId , @PathVariable("count") int taskCount ) {
        HashMap<String, Double> metricsDetails = new HashMap<>();
        if (!playerService.checkIfPlayerIdExists(playerId))
            return metricsDetails;

        long[] responseTimes = new long[taskCount];
        long totalResponseTime = 0;

        ExecutorService service = Executors.newFixedThreadPool(100);
        TaskLimitSemaphore taskLimitSemaphore = new TaskLimitSemaphore(service, 10);

        for (int i = 0; i < taskCount; i++) {
            Future<Long> future = null;
            try {
                future = taskLimitSemaphore.submit(new GetPlayerInfoTask(playerId));
                responseTimes[i] = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            totalResponseTime += responseTimes[i];
        }

        service.shutdown();
        return PerfTestDetails.getPerfMetricDetails(metricsDetails , taskCount , responseTimes , totalResponseTime);
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
    public @ResponseBody Map<String , Double> bulkCallPlayerStats(@PathVariable("playerId") int playerId ,  @PathVariable("matchId") int matchId , @PathVariable("count") int taskCount ){
        HashMap<String , Double > metricsDetails = new HashMap<>();
        if(!playerService.checkIfPlayerIdExists(playerId) && cricketMatchService.checkIfMatchExists(matchId))
            return metricsDetails;

        long[] responseTimes = new long[taskCount];
        long totalResponseTime = 0;

        ExecutorService service = Executors.newFixedThreadPool(100);
        TaskLimitSemaphore taskLimitSemaphore = new TaskLimitSemaphore(service , 10);

        for(int i = 0 ; i < taskCount ; i++){
            Future<Long> future = null;
            try {
                future = taskLimitSemaphore.submit(new GetPlayerStatTask(playerId , matchId));
                responseTimes[i] = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            totalResponseTime += responseTimes[i];
        }

        service.shutdown();
        return PerfTestDetails.getPerfMetricDetails(metricsDetails , taskCount , responseTimes , totalResponseTime);
    }

    private final class GetPlayerInfoTask implements Callable<Long> {
        private final int playerId;

        public GetPlayerInfoTask(int playerId) {
            this.playerId = playerId;
        }

        @Override
        public Long call() throws Exception {
            long startTime =  System.currentTimeMillis();
            getPlayerInfo(this.playerId);
            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }

    private final class GetPlayerStatTask implements Callable<Long> {
        private final int playerId , matchId;

        public GetPlayerStatTask(int playerId , int matchId) {
            this.playerId = playerId;
            this.matchId = matchId;
        }

        @Override
        public Long call() throws Exception {
            long startTime =  System.currentTimeMillis();
            getPlayerStats(this.playerId , this.matchId);
            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }

}
