package com.tekion.dto;

import com.tekion.enums.PlayerRoles;

public class Player {
    private String playerName;
    private int playerScore;
    private int ballsPlayed;
    private int wicketsTaken;
    private int bowlsBowled;
    private PlayerRoles playerRole;

    public Player(String playerName , PlayerRoles playerRole){
        this.playerName = playerName;
        this.playerRole = playerRole;
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
}
