package com.tekion.cricketGame.teamService;

import com.tekion.cricketGame.constants.MatchConstants;
import com.tekion.cricketGame.cricketMatchService.dto.CricketMatchDto;
import com.tekion.cricketGame.playerService.repo.PlayerRepository;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import com.tekion.cricketGame.teamService.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepo;
    private final PlayerRepository playerRepo;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepo , PlayerRepository playerRepo ){
        this.teamRepo = teamRepo;
        this.playerRepo = playerRepo;
    }

    @Override
    public String loadTeamDetails(String teamName){
        if(!teamRepo.ifCheckTeamExists(teamName))
            teamRepo.createTeam(teamName);
        return teamName;
    }

    public void setPlayerDetails(CricketMatchDto cricketMatch){
        setDefaultPlayersList(cricketMatch.getTeam1());
        setDefaultPlayersList(cricketMatch.getTeam2());
    }

    private void setDefaultPlayersList(TeamDto team){
        String playerName;
        int teamId = teamRepo.getIdByTeamName(team.getTeamName());
        for(int player = 1; player <= MatchConstants.TEAM_SIZE ; player++){
            playerName = team.getTeamName() + "_Player" + player;
            if(!playerRepo.ifCheckPlayerExists(teamId , playerName))
                playerRepo.createPlayer(teamId , playerName);
            team.addDefaultPlayer(player ,playerName);
        }
    }

}
