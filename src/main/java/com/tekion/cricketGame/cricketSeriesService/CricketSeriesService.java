package com.tekion.cricketGame.cricketSeriesService;

import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import com.tekion.cricketGame.cricketSeriesService.dto.SeriesRequestDto;
import org.springframework.stereotype.Service;

@Service
public interface CricketSeriesService {
   CricketSeriesBean beginSeries(SeriesRequestDto newSeries);
   boolean checkIfSeriesExists(int seriesId);
   CricketSeriesBean getSeriesDetails(int seriesId);
}
