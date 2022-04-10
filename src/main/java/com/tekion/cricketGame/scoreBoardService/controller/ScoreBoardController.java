package com.tekion.cricketGame.scoreBoardService.controller;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.scoreBoardService.ScoreBoardService;
import com.tekion.cricketGame.scoreBoardService.bean.MatchScoreBoardBean;
import com.tekion.cricketGame.utils.PerfTestDetails;
import com.tekion.cricketGame.utils.TaskLimitSemaphore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

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
    public @ResponseBody Map<String , Double> bulkCallGetScoreboard(@PathVariable("id") int matchId , @PathVariable("count") int taskCount ){
        HashMap<String , Double > metricsDetails = new HashMap<>();
        if(!cricketMatchService.checkIfMatchExists(matchId))
            return metricsDetails;

        long[] responseTimes = new long[taskCount];
        long totalResponseTime = 0;

        ExecutorService service = Executors.newFixedThreadPool(100);
        TaskLimitSemaphore taskLimitSemaphore = new TaskLimitSemaphore(service , 10);

        for(int i = 0 ; i < taskCount ; i++){
            Future<Long> future = null;
            try {
                future = taskLimitSemaphore.submit(new GetScoreboardTask(matchId));
                responseTimes[i] = future.get();
            } catch (InterruptedException | ExecutionException e ) {
                e.printStackTrace();
            }
            totalResponseTime += responseTimes[i];
        }

        service.shutdown();
        return PerfTestDetails.getPerfMetricDetails(metricsDetails , taskCount , responseTimes , totalResponseTime);
    }

    private final class GetScoreboardTask implements Callable<Long> {
        private final int matchId;

        public GetScoreboardTask(int matchId) {
            this.matchId = matchId;
        }

        @Override
        public Long call() throws Exception {
            long startTime =  System.currentTimeMillis();
            fetchScoreBoardDetails(this.matchId);
            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }
}
