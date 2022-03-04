package com.tekion.cricketGame.teamService.repo;

public interface TeamRepository {
    Boolean ifCheckTeamExists(String teamName);
    void createTeam(String teamName);
    int getIdByTeamName(String teamName);
}
