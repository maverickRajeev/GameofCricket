package com.tekion.cricketGame.repo;

import java.sql.SQLException;

public interface TeamRepository {
    Boolean ifCheckTeamExists(String teamName);
    void createTeam(String teamName);
    int getIdByTeamName(String teamName);
}
