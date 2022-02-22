package com.tekion.dto;

import java.util.Scanner;

import com.tekion.constants.RunConstants;
import com.tekion.enums.*;
import com.tekion.utils.MatchCalculationsUtils;

public class Match {
    private int overs;
    private Team team1 , team2;
    private Team teamBattingFirst , teamFieldingFirst;
    private int targetScore = 0;

    public void setMatchOvers(int numberOfOvers){
        this.overs = numberOfOvers;
    }

    public int getMatchOvers(){
        return this.overs;
    }

    public void setTeam1(Team team1){
        this.team1 = team1;
    }

    public void setTeam2(Team team2){
        this.team2 = team2;
    }

    public Team getTeam1(){
        return this.team1;
    }

    public Team getTeam2(){
        return this.team2;
    }

    public void playFirstInning(){
            System.out.println("\n** Start of 1st inning **");
            this.playInning(this.teamBattingFirst , 1);
            this.targetScore = teamBattingFirst.getTeamScore() + 1;
    }

    public void inningsBreak(){
        System.out.println("\n** Innings break **");
        this.displayScore(teamBattingFirst);
        System.out.println("Target : " + this.targetScore);
        System.out.println(teamFieldingFirst.getTeamName() + " need " + this.targetScore + " runs in " + this.overs * 6 + " balls.");
    }

    public void playSecondInning(){
        System.out.println("\n** Start of 2nd inning **");
        this.playInning(this.teamFieldingFirst , 2);

    }

    public void playInning(Team team, int inning){
        for(int i = 0 ; i < this.overs ; i++){
            System.out.println("\nOver : " + (i+1));
            playOver(team , inning , overs);
            if(inning == 2 && team.getTeamScore() >= this.targetScore)
                break;
            if(team.getWicketsFallen() == 10)
                break;
        }
    }

    public void playOver(Team team , int inning , int matchOvers){
        int ballScore;
        for(int ball = 0 ; ball < 6 ; ball++){
            ballScore = playBall(matchOvers, team);
            team.increaseBallsPlayed();
            if(ballScore == 7){
                team.fallWicket();
                System.out.print("W ");
            }else{
                team.increaseTeamScore(ballScore);
                System.out.print(ballScore + " ");
            }
            if(inning == 2 && team.getTeamScore() >= this.targetScore)
                break;
            if(team.getWicketsFallen() == 10)
                break;
        }
        team.getStrikeDetails().changeStrike();
        this.displayScore(team);
        if(inning == 2 && team.getTeamScore() < this.targetScore){
            System.out.println(teamFieldingFirst.getTeamName() + " need " + (this.targetScore-teamFieldingFirst.getTeamScore()) + " runs in " + (this.overs * 6 - teamFieldingFirst.getTotalBallsPlayed()) + " balls.");
        }
    }

    public int playBall(int matchOvers , Team team){
        Player currentBatsman = team.getPlayerById(team.getStrikeDetails().getCurrentStrike());
        int runScored = MatchCalculationsUtils.eachBallScore(matchOvers , currentBatsman);
        if( runScored == RunConstants.WICKET){
            currentBatsman.increaseBallsPlayed();
            currentBatsman.updatePlayerStatus(PlayerStatus.OUT);
            team.getStrikeDetails().fallOfWicket();
        }else{
            currentBatsman.updatePlayerStatus(PlayerStatus.NOT_OUT);
            currentBatsman.increaseScore(runScored);
            currentBatsman.increaseBallsPlayed();
            if(runScored % 2 == 1){
                team.getStrikeDetails().changeStrike();
            }
        }
        return runScored;
    }

    public void displayResult(){
        System.out.println("\n** Match Result **");
        this.displayScore(teamBattingFirst);
        this.displayScore(teamFieldingFirst);

        int runs1 = teamBattingFirst.getTeamScore();
        System.out.println("\n" + teamBattingFirst.getTeamName() + " Batting Scorecard \n");
        teamBattingFirst.displayTeamScorecard();

        int runs2 = teamFieldingFirst.getTeamScore();
        System.out.println("\n" + teamFieldingFirst.getTeamName() + " Batting Scorecard \n");
        teamFieldingFirst.displayTeamScorecard();

        if(runs1 > runs2){
            System.out.println("\n" + teamBattingFirst.getTeamName() + " won by " + (runs1-runs2) + " runs.");
        }else if(runs1 < runs2){
            System.out.println("\n" + teamFieldingFirst.getTeamName() + " won by " + (10 - teamFieldingFirst.getWicketsFallen()) + " wickets.");
        }else{
            System.out.println("\nMatch tied.");
        }


    }

    public void displayScore(Team team){
        System.out.printf("\n%s: %d/%d (%d.%d Overs)%n",
                team.getTeamName(),
                team.getTeamScore(),
                team.getWicketsFallen(),
                team.getTotalBallsPlayed()/6,
                team.getTotalBallsPlayed()%6);
    }
}
