package com.tekion.dtos;

import java.util.ArrayList;
import java.util.Scanner;
import com.tekion.constants.MatchConstants;
import com.tekion.enums.PlayerRoles;

public class Team {
    private String teamName;
    private int teamScore;
    private int wicketsFallen;
    private int totalBallsPlayed;
    private ArrayList<Player> players;

    public Team(String teamName){
        this.teamName = teamName;
        this.teamScore = 0;
        this.wicketsFallen = 0;
        this.totalBallsPlayed = 0;
        this.players = new ArrayList<Player>();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setPlayersList(){
        Scanner sc = new Scanner(System.in);
        String name;
        PlayerRoles playerRole;
        for(int player = 1 ; player <= MatchConstants.TEAM_SIZE ; player++){
            System.out.println("Enter Player - " + player + " Name : ");
            name = sc.nextLine();
            System.out.println("Enter Player - " + player + " Role(BATSMAN/ALLROUNDER/BOWLER): ");
            playerRole = PlayerRoles.valueOf(sc.nextLine().toUpperCase());
            this.players.add(new Player(name , playerRole));
        }
    }

    public void setDefaultPlayersList(){
        String playerName;
        PlayerRoles playerRole;
        for(int player = 1 ; player <= MatchConstants.TEAM_SIZE ; player++){
            playerName = this.getTeamName() + "_Player" + player;
            if(player <=5)
                playerRole = PlayerRoles.BATSMAN;
            else if(player == 6 || player == 7)
                playerRole = PlayerRoles.ALLROUNDER;
            else
                playerRole = PlayerRoles.BOWLER;
            this.players.add(new Player(playerName , playerRole));
        }
    }

    public void getPlayersList(){
        for(int player = 0 ; player < MatchConstants.TEAM_SIZE ; player++){
            System.out.println(this.players.get(player).getPlayerName() + " - " + this.players.get(player).getPlayerRole() );
        }
    }

    public int getTeamScore() {
        return this.teamScore;
    }

    public int getWicketsFallen() {
        return this.wicketsFallen;
    }

    public int getTotalBallsPlayed(){
        return this.totalBallsPlayed;
    }

    public void increaseTeamScore(int score) {
        this.teamScore += score;
    }

    public void fallWicket(){
        this.wicketsFallen++;
    }

    public void increaseBallsPlayed(){
        this.totalBallsPlayed++;
    }

}
