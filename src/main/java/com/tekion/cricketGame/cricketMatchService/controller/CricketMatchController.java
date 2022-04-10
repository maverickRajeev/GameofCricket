package com.tekion.cricketGame.cricketMatchService.controller;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.cricketMatchService.bean.CricketMatchBean;
import com.tekion.cricketGame.cricketSeriesService.CricketSeriesService;
import com.tekion.cricketGame.utils.PerfTestDetails;
import com.tekion.cricketGame.utils.TaskLimitSemaphore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

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

    // we either need to provide x (SQL )  or y (SQL)
    @GetMapping("/bulkMatchDetails/{id}/{count}")
    public @ResponseBody Map<String , Double> bulkCallGetMatchDetails(@PathVariable("id") int matchId , @PathVariable("count") int taskCount ){
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
                future = taskLimitSemaphore.submit(new GetMatchDetailsTask(matchId));
                responseTimes[i] = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            totalResponseTime += responseTimes[i];
        }

        service.shutdown();
        return PerfTestDetails.getPerfMetricDetails(metricsDetails , taskCount , responseTimes , totalResponseTime);
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
    public @ResponseBody Map<String , Double> bulkCallGetMatchList(@PathVariable("id") int seriesId , @PathVariable("count") int taskCount ){
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
                future = taskLimitSemaphore.submit(new GetMatchListTask(seriesId));
                responseTimes[i] = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            totalResponseTime += responseTimes[i];
        }

        service.shutdown();
        return PerfTestDetails.getPerfMetricDetails(metricsDetails , taskCount , responseTimes , totalResponseTime);
    }

    private final class GetMatchDetailsTask implements Callable<Long> {
        private final int matchId;

        public GetMatchDetailsTask(int matchId) {
            this.matchId = matchId;
        }

        @Override
        public Long call() throws Exception {
            long startTime =  System.currentTimeMillis();
            getMatchDetails(this.matchId);
            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }

    private final class GetMatchListTask implements Callable<Long> {
        private final int seriesId;

        public GetMatchListTask(int seriesId) {
            this.seriesId = seriesId;
        }

        @Override
        public Long call() throws Exception {
            long startTime =  System.currentTimeMillis();
            getAllMatchesBySeriesId(this.seriesId);
            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }
}
