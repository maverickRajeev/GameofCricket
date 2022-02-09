package com.tekion.gameComponents;

import java.util.ArrayList;

public class Team {
    private String teamName;
    private int teamScore;
    private int wicketsFallen;
    private int totalBallsPlayed;
    private ArrayList <Player> players;

    public Team(String teamName){
        this.teamName = teamName;
        this.teamScore = 0;
        this.wicketsFallen = 0;
        this.totalBallsPlayed = 0;
        this.players = new ArrayList<>();
    }

    public String getTeamName() {
        return teamName;
    }

    public int getTeamScore() {
        return teamScore;
    }

    public int getWicketsFallen() {
        return wicketsFallen;
    }

    public int getTotalBallsPlayed(){
        return totalBallsPlayed;
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
