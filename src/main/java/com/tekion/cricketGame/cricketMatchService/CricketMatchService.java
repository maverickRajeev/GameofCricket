package com.tekion.cricketGame.cricketMatchService;

import com.tekion.cricketGame.cricketSeriesService.dto.CricketSeriesDto;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import org.springframework.stereotype.Service;

@Service
public interface CricketMatchService {
    TeamDto startCricketMatch(CricketSeriesDto cricketSeries);
}
