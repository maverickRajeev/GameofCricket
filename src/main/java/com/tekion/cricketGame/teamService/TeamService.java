package com.tekion.cricketGame.teamService;

import com.tekion.cricketGame.cricketMatchService.dto.MatchDto;

public interface TeamService {
    String inputTeam1Details();
    String inputTeam2Details();
    void setPlayerDetails(MatchDto cricketMatch);
}
