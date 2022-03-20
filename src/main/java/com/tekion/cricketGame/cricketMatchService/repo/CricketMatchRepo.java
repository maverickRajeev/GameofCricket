package com.tekion.cricketGame.cricketMatchService.repo;

import com.tekion.cricketGame.cricketMatchService.bean.CricketMatchBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CricketMatchRepo {
    int createMatch(CricketMatchBean cricketMatchBean);
    boolean checkMatchId(int matchId);
    CricketMatchBean getMatchDetailsById(int matchId);
    List<CricketMatchBean> getAllMatchesBySeriesId(int seriesId);
}
