package com.tekion.cricketGame.teamService;

import com.tekion.cricketGame.constants.MatchConstants;
import com.tekion.cricketGame.cricketMatchService.dto.CricketMatchDto;
import com.tekion.cricketGame.playerService.bean.PlayerBean;
import com.tekion.cricketGame.playerService.repo.PlayerRepository;
import com.tekion.cricketGame.teamService.bean.TeamBean;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import com.tekion.cricketGame.teamService.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepo;
    private final PlayerRepository playerRepo;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepo, PlayerRepository playerRepo) {
        this.teamRepo = teamRepo;
        this.playerRepo = playerRepo;
    }

    @Override
    public String loadTeamDetails(String teamName) {
        if (!teamRepo.ifCheckTeamExists(teamName))
            teamRepo.createTeam(teamName);
        return teamName;
    }

    @Override
    public boolean checkIfTeamIdExists(int teamId){
       return teamRepo.checkIfTeamIdExists(teamId);
    }

    @Override
    @Cacheable(cacheNames = "teamCache" , key = "#teamId")
    public TeamBean getTeamDetails(int teamId) {
        return teamRepo.getTeamDetails(teamId);
    }

    @Override
    public CricketMatchDto setPlayerDetails(CricketMatchDto cricketMatch) {
        cricketMatch = setDefaultPlayersList(cricketMatch, 1);
        cricketMatch = setDefaultPlayersList(cricketMatch, 2);
        return cricketMatch;
    }

    private CricketMatchDto setDefaultPlayersList(CricketMatchDto cricketMatch, int teamNumber) {
        String playerName;
        int teamId;
        TeamDto team;

        if (teamNumber == 1)
            team = cricketMatch.getTeam1();
        else
            team = cricketMatch.getTeam2();

        teamId = teamRepo.getIdByTeamName(team.getTeamName());

        for (int player = 1; player <= MatchConstants.TEAM_SIZE; player++) {
            playerName = team.getTeamName() + "_Player" + player;
            if (!playerRepo.ifCheckPlayerExists(teamId, playerName))
                playerRepo.createPlayer(teamId, playerName);
        }

        List<PlayerBean> players = playerRepo.getAllPlayersByTeamId(teamId);

        int playerNumber = 1;
        for (PlayerBean player : players) {
            team.addDefaultPlayer(playerNumber, player.getPlayerName());
            playerNumber++;
        }

        if (teamNumber == 1)
            cricketMatch.setTeam1(team);
        else
            cricketMatch.setTeam2(team);

        return cricketMatch;
    }

}
