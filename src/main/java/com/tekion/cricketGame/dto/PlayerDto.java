package com.tekion.cricketGame.dto;

import com.tekion.cricketGame.enums.PlayerRoles;
import com.tekion.cricketGame.enums.PlayerStatus;

public class PlayerDto {
    private String playerName;
    private int playerScore;
    private int ballsPlayed;
    private int wicketsTaken;
    private int bowlsBowled;
    private PlayerRoles playerRole;
    private PlayerStatus playerStatus;

    public PlayerDto(String playerName , PlayerRoles playerRole){
        this.playerName = playerName;
        this.playerRole = playerRole;
        this.playerStatus = PlayerStatus.YET_TO_BAT;
        this.playerScore = 0;
        this.ballsPlayed = 0;
        this.wicketsTaken = 0;
        this.bowlsBowled = 0;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public PlayerRoles getPlayerRole() {
        return this.playerRole;
    }

    public int getPlayerScore() {
        return this.playerScore;
    }

    public int getBallsPlayed() {
        return this.ballsPlayed;
    }

    public void increaseScore(int score) {
        this.playerScore += score;
    }

    public void increaseBallsPlayed(){
        this.ballsPlayed++;
    }

    public void updatePlayerStatus(PlayerStatus playerStatus) {
        this.playerStatus = playerStatus;
    }

    public PlayerStatus getPlayerStatus() {
        return this.playerStatus;
    }
}
