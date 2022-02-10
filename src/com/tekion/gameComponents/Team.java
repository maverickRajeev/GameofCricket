package com.tekion.gameComponents;

import java.util.ArrayList;
import java.util.Scanner;

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
        this.players = new ArrayList<Player>();
    }

    public String getTeamName() {
        return teamName;
    }

    public void setPlayersList(){
        Scanner sc = new Scanner(System.in);
        for(int i = 0 ; i < 11 ; i++){
            System.out.println("Enter Player - " + (i+1) + " Name : ");
            this.players.add(new Player(sc.nextLine()));
        }
    }

    public void getPlayersList(){
        for(int i = 0 ; i < 11 ; i++){
            System.out.println(this.players.get(i).getPlayerName());
        }
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
