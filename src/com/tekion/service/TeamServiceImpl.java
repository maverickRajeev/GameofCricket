package com.tekion.service;

import com.tekion.constants.MatchConstants;
import com.tekion.dto.Match;
import com.tekion.dto.Team;
import com.tekion.repo.PlayerRepository;
import com.tekion.repo.PlayerRepositoryImpl;
import com.tekion.repo.TeamRepository;
import com.tekion.repo.TeamRepositoryImpl;

import java.util.Scanner;

public class TeamServiceImpl implements TeamService {
    TeamRepository teamRepo = new TeamRepositoryImpl();
    PlayerRepository playerRepo = new PlayerRepositoryImpl();

    public String inputTeam1Details(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter name of Team-1 : ");
        String teamNameInput = sc.nextLine().toUpperCase();
        if(!teamRepo.ifCheckTeamExists(teamNameInput))
            teamRepo.createTeam(teamNameInput);
        return teamNameInput;
    }

    public String inputTeam2Details(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter name of Team-2 : ");
        String teamNameInput = sc.nextLine().toUpperCase();
        if(!teamRepo.ifCheckTeamExists(teamNameInput))
            teamRepo.createTeam(teamNameInput);
        return teamNameInput;
    }

    public void setPlayerDetails(Match cricketMatch){
        Scanner sc = new Scanner(System.in);
        System.out.println("Use default names(TeamName_PlayerNo) and player roles(Y/N) ? : ");
        char userInputForDefaultPlayersList = sc.next().toUpperCase().charAt(0);
        if(userInputForDefaultPlayersList == 'Y'){
            setDefaultPlayersList(cricketMatch.getTeam1());
            setDefaultPlayersList(cricketMatch.getTeam2());
        }else if(userInputForDefaultPlayersList == 'N') {
            setPlayersList(cricketMatch.getTeam1());
            setPlayersList(cricketMatch.getTeam2());
        }else{
            System.out.println("Incorrect choice.");
            System.exit(0);
        }
    }

    private void setDefaultPlayersList(Team team){
        String playerName;
        int teamId = teamRepo.getIdByTeamName(team.getTeamName());
        for(int player = 1; player <= MatchConstants.TEAM_SIZE ; player++){
            playerName = team.getTeamName() + "_Player" + player;
            if(!playerRepo.ifCheckPlayerExists(teamId , playerName))
                playerRepo.createPlayer(teamId , playerName);
            team.addDefaultPlayer(player ,playerName);
        }
    }

    private void setPlayersList(Team team){
        Scanner sc = new Scanner(System.in);
        String playerName;
        int teamId = teamRepo.getIdByTeamName(team.getTeamName());
        System.out.println("\nEnter Player Details for Team - " + team.getTeamName());
        for(int player = 1 ; player <= MatchConstants.TEAM_SIZE ; player++){
            System.out.println("Enter Player - " + player + " Name : ");
            playerName = sc.nextLine();
            if(!playerRepo.ifCheckPlayerExists(teamId , playerName))
                playerRepo.createPlayer(teamId , playerName);
            team.addPlayer(playerName);
        }
    }
}
