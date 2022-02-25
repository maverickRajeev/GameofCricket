package com.tekion.service;

import com.tekion.dto.Match;

public interface TeamService {
    String inputTeam1Details();
    String inputTeam2Details();
    void setPlayerDetails(Match cricketMatch);
}
