package com.tekion.gameComponents;

import java.util.ArrayList;

public class Team {
    private String teamName;
    private int teamScore;
    private int wicketsFallen;
    private int totalAvailableBalls;
    private ArrayList <Player> players;

    public Team(String teamName , int totalBalls){
        this.teamName = teamName;
        this.teamScore = 0;
        this.wicketsFallen = 0;
        this.totalAvailableBalls = totalBalls;
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

    public void increaseTeamScore(int score) {
        this.teamScore += score;
    }

    public void fallWicket(){
        this.wicketsFallen++;
    }

    public void decreaseAvailableBalls(){
        this.totalAvailableBalls--;
    }

}
