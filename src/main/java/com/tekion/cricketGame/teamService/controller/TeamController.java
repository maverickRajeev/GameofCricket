package com.tekion.cricketGame.teamService.controller;

import com.tekion.cricketGame.teamService.TeamService;
import com.tekion.cricketGame.teamService.bean.TeamBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
