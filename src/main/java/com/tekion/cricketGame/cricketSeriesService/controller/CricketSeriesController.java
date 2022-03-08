package com.tekion.cricketGame.cricketSeriesService.controller;

import com.tekion.cricketGame.cricketSeriesService.CricketSeriesService;
import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import com.tekion.cricketGame.cricketSeriesService.dto.SeriesRequestDto;
import com.tekion.cricketGame.validator.RequestValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/series")
public class CricketSeriesController {

    @Autowired
    private CricketSeriesService cricketSeriesService;

    @PostMapping(value = "/newSeries" , consumes = MediaType.APPLICATION_JSON_VALUE , produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody CricketSeriesBean startSeries(@RequestBody SeriesRequestDto newSeries) {
      CricketSeriesBean seriesInfo = new CricketSeriesBean();
      if(RequestValidator.seriesRequestValidator(newSeries)) {
          seriesInfo = cricketSeriesService.beginSeries(newSeries);
          return seriesInfo;
      }
      return seriesInfo;
    }

    @GetMapping(value = "/seriesDetails/{id}")
    public @ResponseBody ResponseEntity<CricketSeriesBean> getSeriesDetails(@PathVariable("id") int seriesId){
        CricketSeriesBean seriesInfo = new CricketSeriesBean();
        if(cricketSeriesService.checkIfSeriesExists(seriesId)) {
            seriesInfo = cricketSeriesService.getSeriesDetails(seriesId);
        }
        return ResponseEntity.ok(seriesInfo);
    }

}

