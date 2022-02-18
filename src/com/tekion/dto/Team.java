package com.tekion.dto;

import java.util.ArrayList;
import java.util.Scanner;
import com.tekion.constants.MatchConstants;
import com.tekion.enums.PlayerRoles;
import com.tekion.enums.PlayerStatus;

public class Team {
    private final String teamName;
    private int teamScore;
    private int wicketsFallen;
    private int totalBallsPlayed;
    private final ArrayList<Player> players;
    private BattingStatus teamStrike;

    public Team(String teamName){
        this.teamName = teamName;
        this.teamScore = 0;
        this.wicketsFallen = 0;
        this.totalBallsPlayed = 0;
        this.players = new ArrayList<>();
        this.teamStrike = new BattingStatus();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setPlayersList(){
        Scanner sc = new Scanner(System.in);
        String name;
        PlayerRoles playerRole;
        System.out.println("\nEnter Player Details for Team - " + this.getTeamName());
        for(int player = 1 ; player <= MatchConstants.TEAM_SIZE ; player++){
            System.out.println("Enter Player - " + player + " Name : ");
            name = sc.nextLine();
            System.out.println("Enter Player - " + player + " Role(BATSMAN/ALLROUNDER/BOWLER): ");
            try {
                playerRole = PlayerRoles.valueOf(sc.nextLine().toUpperCase());
                this.players.add(new Player(name , playerRole));
            }catch (IllegalArgumentException e){
                System.out.println("Incorrect Player Role.");
                System.exit(0);
            }
        }
    }

    public void setDefaultPlayersList(){
        String playerName;
        PlayerRoles playerRole;
        for(int player = 1 ; player <= MatchConstants.TEAM_SIZE ; player++){
            playerName = this.getTeamName() + "_Player" + player;
            if(player <= 5)
                playerRole = PlayerRoles.BATSMAN;
            else if(player == 6 || player == 7)
                playerRole = PlayerRoles.ALLROUNDER;
            else
                playerRole = PlayerRoles.BOWLER;
            this.players.add(new Player(playerName , playerRole));
        }
    }

    public void displayPlayersList(){
        for(int player = 0 ; player < MatchConstants.TEAM_SIZE ; player++){
            System.out.println(this.players.get(player).getPlayerName() + " - " + this.players.get(player).getPlayerRole() );
        }
    }

    public void displayTeamScorecard(){
        for(int player = 0 ; player < MatchConstants.TEAM_SIZE ; player++){
            Player playerNum = this.players.get(player);
            if(playerNum.getPlayerStatus() == PlayerStatus.OUT)
                System.out.println(playerNum.getPlayerName() + " - " + playerNum.getPlayerScore() + "(" + playerNum.getBallsPlayed() +")");
            else if(playerNum.getPlayerStatus() == PlayerStatus.NOT_OUT)
                System.out.println(playerNum.getPlayerName() + " - " + playerNum.getPlayerScore() + "(" + playerNum.getBallsPlayed() +")" + " NOT OUT");
            else
                System.out.println(playerNum.getPlayerName() + " - " + "YET TO BAT");
        }
    }

    public Player getPlayerById(int playerId){
         return this.players.get(playerId);
    }

    public BattingStatus getStrikeDetails(){
        return this.teamStrike;
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
