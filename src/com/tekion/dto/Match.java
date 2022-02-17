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

    public void playMatch(){
        this.setupMatch();
        this.setTeamInfo();
        this.showTeamInfo();
        this.coinToss();
        this.playFirstInning();
        this.inningsBreak();
        this.playSecondInning();
        this.displayResult();
    }

    private void setupMatch(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose match type (T20/0DI): ");
        String userInput = sc.nextLine();
        try {
            TypesOfMatch matchType = TypesOfMatch.valueOf(userInput.toUpperCase());
            this.overs = matchType.getMatchType();
        }catch (IllegalArgumentException e){
            System.out.println("Incorrect Match Type.");
            System.exit(0);
        }
    }

    private void setTeamInfo(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter name of Team-1 : ");
        this.team1 = new Team(sc.nextLine());
        System.out.println("Enter name of Team-2 : ");
        this.team2 = new Team(sc.nextLine());
        System.out.println("Use default names and player roles(Y/N) ? : ");
        char userInput = sc.next().toUpperCase().charAt(0);
        if(userInput == 'Y'){
            team1.setDefaultPlayersList();
            team2.setDefaultPlayersList();
        }else if(userInput == 'N') {
            team1.setPlayersList();
            team2.setPlayersList();
        }else{
            System.out.println("Incorrect choice.");
            System.exit(0);
        }
    }

    private void showTeamInfo(){
        System.out.println("\n** Team-1 List **");
        team1.displayPlayersList();
        System.out.println("\n** Team-2 List **");
        team2.displayPlayersList();
    }

    private void coinToss() {
        System.out.println("\nLet's have a coin toss.");
        int tossResult = MatchCalculationsUtils.coinTossResult();
        if (tossResult == 1) {
            System.out.println(team1.getTeamName() + " won the toss. Please choose (BAT/FIELD).");
            chooseBatOrField(team1 , team2);
        } else {
            System.out.println(team2.getTeamName() + " won the toss. Please choose (BAT/FIELD).");
            chooseBatOrField(team2 , team1);
        }
    }

    private void chooseBatOrField(Team tossWinner , Team tossLoser){
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        try {
            TossChoices tossChoice = TossChoices.valueOf(userInput.toUpperCase());
            if(tossChoice.choseBatting()){
                this.teamBattingFirst = tossWinner;
                this.teamFieldingFirst = tossLoser;
            }else{
                this.teamBattingFirst = tossLoser;
                this.teamFieldingFirst = tossWinner;
            }
            System.out.println(tossWinner.getTeamName() + " chose to " + tossChoice + " first." );
        }catch (IllegalArgumentException e){
            System.out.println("Incorrect choice made.");
            System.exit(0);
        }
    }

    private void playFirstInning(){
            System.out.println("\n** Start of 1st inning **");
            this.playInning(this.teamBattingFirst , 1);
            this.targetScore = teamBattingFirst.getTeamScore() + 1;
    }

    private void inningsBreak(){
        System.out.println("\n** Innings break **");
        this.displayScore(teamBattingFirst);
        System.out.println("Target : " + this.targetScore);
        System.out.println(teamFieldingFirst.getTeamName() + " need " + this.targetScore + " runs in " + this.overs * 6 + " balls.");
    }

    private void playSecondInning(){
        System.out.println("\n** Start of 2nd inning **");
        this.playInning(this.teamFieldingFirst , 2);

    }

    private void playInning(Team team, int inning){
        for(int i = 0 ; i < this.overs ; i++){
            System.out.println("\nOver : " + (i+1));
            playOver(team , inning , overs);
            if(inning == 2 && team.getTeamScore() >= this.targetScore)
                break;
            if(team.getWicketsFallen() == 10)
                break;
        }
    }

    private void playOver(Team team , int inning , int matchOvers){
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

    private int playBall(int matchOvers , Team team){
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

    private void displayResult(){
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

    private void displayScore(Team team){
        System.out.printf("\n%s: %d/%d (%d.%d Overs)%n",
                team.getTeamName(),
                team.getTeamScore(),
                team.getWicketsFallen(),
                team.getTotalBallsPlayed()/6,
                team.getTotalBallsPlayed()%6);
    }
}
