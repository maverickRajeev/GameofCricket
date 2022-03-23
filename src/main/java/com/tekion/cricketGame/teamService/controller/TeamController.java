package com.tekion.cricketGame.teamService.controller;

import com.tekion.cricketGame.teamService.TeamService;
import com.tekion.cricketGame.teamService.bean.TeamBean;
import com.tekion.cricketGame.utils.MathCalculations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

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
    public @ResponseBody Map<String , Double> bulkCallTeamInfo(@PathVariable("id") int teamId , @PathVariable("count") int threadCount ){
        HashMap<String , Double> metricsDetails = new HashMap<>();
        if(!teamService.checkIfTeamIdExists(teamId))
            return metricsDetails;

        long[] responseTimes = new long[threadCount];
        long startTime = System.currentTimeMillis();
        long elapsedTime = 0 , currentTime = 0;
        for(int i = 0 ; i < threadCount ; i++){
            getTeamInfo(teamId);
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
