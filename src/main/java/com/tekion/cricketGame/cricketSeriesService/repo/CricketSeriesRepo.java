package com.tekion.cricketGame.cricketSeriesService.repo;

import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import org.springframework.stereotype.Repository;

@Repository
public interface CricketSeriesRepo {
    void createSeries(CricketSeriesBean cricketSeriesBean);
    CricketSeriesBean getSeriesDetailsById(int seriesId);
}
