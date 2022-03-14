package com.tekion.cricketGame.cricketMatchService;

import com.tekion.cricketGame.cricketMatchService.bean.CricketMatchBean;
import com.tekion.cricketGame.cricketSeriesService.dto.CricketSeriesDto;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CricketMatchService {
    TeamDto startCricketMatch(CricketSeriesDto cricketSeries , int seriesId);
    boolean checkIfMatchExists(int matchId);
    CricketMatchBean getMatchDetails(int matchId);
    List<CricketMatchBean> listAllMatchesBySeriesId(int seriesId);
}
