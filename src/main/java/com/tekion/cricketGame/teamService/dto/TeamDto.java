package com.tekion.cricketGame.teamService.dto;

import com.tekion.cricketGame.constants.MatchConstants;
import com.tekion.cricketGame.enums.PlayerRoles;
import com.tekion.cricketGame.enums.PlayerStatus;
import com.tekion.cricketGame.playerService.dto.PlayerDto;

import java.util.ArrayList;

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
