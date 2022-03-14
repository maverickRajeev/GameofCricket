package com.tekion.cricketGame.cricketSeriesService.repo;

import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import org.springframework.stereotype.Repository;

@Repository
public interface CricketSeriesRepo {
    int createSeries(CricketSeriesBean cricketSeriesBean);
    void updateSeriesByMatch(CricketSeriesBean cricketSeriesBean , int seriesId);
    boolean checkSeriesId(int seriesId);
    CricketSeriesBean getSeriesDetailsById(int seriesId);

}
