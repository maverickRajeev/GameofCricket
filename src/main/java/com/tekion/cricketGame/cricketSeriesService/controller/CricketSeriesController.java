package com.tekion.cricketGame.cricketSeriesService.controller;

import com.tekion.cricketGame.cricketSeriesService.CricketSeriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CricketSeriesController {

    private final CricketSeriesService cricketSeriesService;

    @Autowired
    public CricketSeriesController(CricketSeriesService cricketSeriesService){
        this.cricketSeriesService = cricketSeriesService;
    }

    @GetMapping(value = "/startSeries")
    public void startSeries(){
        cricketSeriesService.beginSeries();
    }

}
