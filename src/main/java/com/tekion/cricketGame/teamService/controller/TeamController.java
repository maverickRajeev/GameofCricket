package com.tekion.cricketGame.teamService.controller;

import com.tekion.cricketGame.teamService.TeamService;
import com.tekion.cricketGame.teamService.bean.TeamBean;
import com.tekion.cricketGame.utils.PerfTestDetails;
import com.tekion.cricketGame.utils.TaskLimitSemaphore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

@RestController
@RequestMapping("/team")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/{id}")
    public @ResponseBody ResponseEntity<TeamBean> getTeamInfo(@PathVariable("id") int teamId){
        TeamBean teamInfo;
        if(teamService.checkIfTeamIdExists(teamId)){
            teamInfo = teamService.getTeamDetails(teamId);
            return ResponseEntity.ok().body(teamInfo);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/bulkTeamDetails/{id}/{count}")
    public @ResponseBody Map<String , Double> bulkCallTeamInfo(@PathVariable("id") int teamId , @PathVariable("count") int taskCount ) {
        HashMap<String , Double> metricsDetails = new HashMap<>();
        if(!teamService.checkIfTeamIdExists(teamId))
            return metricsDetails;

        long[] responseTimes = new long[taskCount];
        long totalResponseTime = 0;

        ExecutorService service = Executors.newFixedThreadPool(100);
        TaskLimitSemaphore taskLimitSemaphore = new TaskLimitSemaphore(service , 10);

        for(int i = 0 ; i < taskCount ; i++){
            Future <Long> future = null;
            try {
                future = taskLimitSemaphore.submit(new GetTeamDetailsTask(teamId));
                responseTimes[i] = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            totalResponseTime += responseTimes[i];
        }

        service.shutdown();
        return PerfTestDetails.getPerfMetricDetails(metricsDetails , taskCount , responseTimes , totalResponseTime);
    }

    private final class GetTeamDetailsTask implements Callable<Long> {
        private final int teamId;

        public GetTeamDetailsTask(int teamId) {
            this.teamId = teamId;
        }

        @Override
        public Long call() throws Exception {
            long startTime =  System.currentTimeMillis();
            getTeamInfo(teamId);
            long endTime = System.currentTimeMillis();
            return endTime - startTime;
        }
    }

}
