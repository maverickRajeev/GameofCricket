package com.tekion.cricketGame.teamService.repo;

import com.tekion.cricketGame.teamService.bean.TeamBean;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository {
    void createTeam(String teamName);
    Boolean ifCheckTeamExists(String teamName);
    Boolean checkIfTeamIdExists(int teamId);
    int getIdByTeamName(String teamName);
    TeamBean getTeamDetails(int teamId);
}
