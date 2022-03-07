package com.tekion.cricketGame.cricketSeriesService.controller;

import com.tekion.cricketGame.cricketSeriesService.CricketSeriesService;
import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import com.tekion.cricketGame.cricketSeriesService.dto.SeriesRequestDto;
import com.tekion.cricketGame.cricketSeriesService.repo.CricketSeriesRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class CricketSeriesController {

    private CricketSeriesService cricketSeriesService;
    private CricketSeriesRepo cricketSeriesRepo;

    public static final Logger logger = LoggerFactory.getLogger(CricketSeriesController.class);

    @Autowired
    public CricketSeriesController(CricketSeriesService cricketSeriesService , CricketSeriesRepo cricketSeriesRepo) {
        this.cricketSeriesService = cricketSeriesService;
        this.cricketSeriesRepo = cricketSeriesRepo;
    }

    @PostMapping(value = "/startSeries" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CricketSeriesBean startSeries(@RequestBody SeriesRequestDto newSeries) {
      cricketSeriesService.beginSeries(newSeries);
      return new CricketSeriesBean();
    }

    @GetMapping(value = "/series/{id}" , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CricketSeriesBean displaySeriesDetails(@PathVariable("id") int seriesId){
        return cricketSeriesRepo.getSeriesDetailsById(seriesId);
    }

}

