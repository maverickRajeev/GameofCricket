package com.tekion.cricketGame.cricketSeriesService;

import com.tekion.cricketGame.cricketSeriesService.dto.SeriesRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface CricketSeriesService {
   void beginSeries(SeriesRequestDto newSeries);
}
