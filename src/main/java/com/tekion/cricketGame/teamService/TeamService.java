package com.tekion.cricketGame.teamService;

import com.tekion.cricketGame.cricketMatchService.dto.CricketMatchDto;
import org.springframework.stereotype.Service;

@Service
public interface TeamService {
    String loadTeamDetails(String teamName);
    void setPlayerDetails(CricketMatchDto cricketMatch);
}
