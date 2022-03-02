package com.tekion.cricketGame.service;

import com.tekion.cricketGame.dto.MatchDto;

public interface TeamService {
    String inputTeam1Details();
    String inputTeam2Details();
    void setPlayerDetails(MatchDto cricketMatch);
}
