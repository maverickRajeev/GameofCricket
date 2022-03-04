package com.tekion.cricketGame.teamService.dto;

import com.tekion.cricketGame.constants.MatchConstants;
import com.tekion.cricketGame.dto.BattingStatusDto;
import com.tekion.cricketGame.playerService.dto.PlayerDto;
import com.tekion.cricketGame.enums.PlayerRoles;
import com.tekion.cricketGame.enums.PlayerStatus;

import java.util.ArrayList;
import java.util.Scanner;

public class TeamDto {
    private final String teamName;
    private final ArrayList<PlayerDto> players;
    private BattingStatusDto teamStrike;

    public TeamDto(String teamName){
        this.teamName = teamName;
        this.players = new ArrayList<>();
        this.teamStrike = new BattingStatusDto();
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
                this.players.add(new PlayerDto(name , playerRole));
            }catch (IllegalArgumentException e){
                System.out.println("Incorrect Player Role.");
                System.exit(0);
            }
        }
    }
    public void addPlayer(String playerName){
        Scanner sc = new Scanner(System.in);
        PlayerRoles playerRole;
        System.out.println("Enter Player - " + playerName + " Role(BATSMAN/ALLROUNDER/BOWLER): ");
        try {
            playerRole = PlayerRoles.valueOf(sc.nextLine().toUpperCase());
            this.players.add(new PlayerDto(playerName , playerRole));
        }catch (IllegalArgumentException e){
            System.out.println("Incorrect Player Role.");
            System.exit(0);
        }
    }

    public void addDefaultPlayer(int playerNumber , String playerName){
        PlayerRoles playerRole;
        if(playerNumber <= 5)
            playerRole = PlayerRoles.BATSMAN;
        else if(playerNumber == 6 || playerNumber == 7)
            playerRole = PlayerRoles.ALLROUNDER;
        else
            playerRole = PlayerRoles.BOWLER;

        this.players.add(new PlayerDto(playerName , playerRole));
    }

    public void displayPlayersList(){
        for(int player = 0 ; player < MatchConstants.TEAM_SIZE ; player++){
            System.out.println(this.players.get(player).getPlayerName() + " - " + this.players.get(player).getPlayerRole() );
        }
    }

    public void displayTeamScorecard(){
        for(int player = 0 ; player < MatchConstants.TEAM_SIZE ; player++){
            PlayerDto playerNum = this.players.get(player);
            if(playerNum.getPlayerStatus() == PlayerStatus.OUT)
                System.out.println(playerNum.getPlayerName() + " - " + playerNum.getPlayerScore() + "(" + playerNum.getBallsPlayed() +")");
            else if(playerNum.getPlayerStatus() == PlayerStatus.NOT_OUT)
                System.out.println(playerNum.getPlayerName() + " - " + playerNum.getPlayerScore() + "(" + playerNum.getBallsPlayed() +")" + " NOT OUT");
            else
                System.out.println(playerNum.getPlayerName() + " - " + "YET TO BAT");
        }
    }

    public PlayerDto getPlayerById(int playerId){
         return this.players.get(playerId);
    }

    public PlayerDto getCurrentBatsMan(){
        return this.getPlayerById(this.teamStrike.getCurrentStrike());
    }

    public void changeTeamStrike(){
        this.teamStrike.changeStrike();
    }

    public void newPlayerChange(){
        this.teamStrike.fallOfWicket();
    }
}
