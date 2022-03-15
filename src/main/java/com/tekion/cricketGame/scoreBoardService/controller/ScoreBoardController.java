package com.tekion.cricketGame.scoreBoardService.controller;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.scoreBoardService.ScoreBoardService;
import com.tekion.cricketGame.scoreBoardService.bean.MatchScoreBoardBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
