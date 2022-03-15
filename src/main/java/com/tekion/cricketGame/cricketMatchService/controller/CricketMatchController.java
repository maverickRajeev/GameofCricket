package com.tekion.cricketGame.cricketMatchService.controller;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.cricketMatchService.bean.CricketMatchBean;
import com.tekion.cricketGame.cricketSeriesService.CricketSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
