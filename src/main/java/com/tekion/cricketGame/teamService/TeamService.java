package com.tekion.cricketGame.teamService;

import com.tekion.cricketGame.cricketMatchService.dto.CricketMatchDto;
import com.tekion.cricketGame.teamService.bean.TeamBean;
import org.springframework.stereotype.Service;

@Service
public interface TeamService {
    String loadTeamDetails(String teamName);
    CricketMatchDto setPlayerDetails(CricketMatchDto cricketMatch);
    boolean checkIfTeamIdExists(int teamId);
    TeamBean getTeamDetails(int teamId);
}
