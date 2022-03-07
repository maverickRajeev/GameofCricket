package com.tekion.cricketGame.teamService.repo;

import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository {
    void createTeam(String teamName);
    Boolean ifCheckTeamExists(String teamName);
    int getIdByTeamName(String teamName);
}
