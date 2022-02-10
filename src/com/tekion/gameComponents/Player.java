package com.tekion.gameComponents;

public class Player {
    private String playerName;
    private enum playerRoles{
        BATSMAN , BOWLER
    };
    private int playerScore;
    private int ballsPlayed;
    private int wicketsTaken;

    public Player(String playerName){
        this.playerName = playerName;
        this.playerScore = 0;
        this.ballsPlayed = 0;
        this.wicketsTaken = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public int getBallsPlayed() {
        return ballsPlayed;
    }

    public void increaseScore(int score) {
        this.playerScore += score;
    }

    public void increaseBallsPlayed(){
        this.ballsPlayed++;
    }
}
