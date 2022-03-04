package com.tekion.cricketGame.cricketMatchService.dto;

import com.tekion.cricketGame.teamService.dto.TeamDto;

public class MatchDto {
    private int overs;
    private TeamDto team1 , team2;

    public void setMatchOvers(int numberOfOvers){
        this.overs = numberOfOvers;
    }

    public int getMatchOvers(){
        return this.overs;
    }

    public void setTeam1(TeamDto team1){
        this.team1 = team1;
    }

    public void setTeam2(TeamDto team2){
        this.team2 = team2;
    }

    public TeamDto getTeam1(){
        return this.team1;
    }

    public TeamDto getTeam2(){
        return this.team2;
    }

//    public void displayResult(){
//        System.out.println("\n** Match Result **");
//        this.displayScore(teamBattingFirst);
//        this.displayScore(teamFieldingFirst);
//
//        int runs1 = teamBattingFirst.getTeamScore();
//        System.out.println("\n" + teamBattingFirst.getTeamName() + " Batting Scorecard \n");
//        teamBattingFirst.displayTeamScorecard();
//
//        int runs2 = teamFieldingFirst.getTeamScore();
//        System.out.println("\n" + teamFieldingFirst.getTeamName() + " Batting Scorecard \n");
//        teamFieldingFirst.displayTeamScorecard();
//
//        if(runs1 > runs2){
//            System.out.println("\n" + teamBattingFirst.getTeamName() + " won by " + (runs1-runs2) + " runs.");
//        }else if(runs1 < runs2){
//            System.out.println("\n" + teamFieldingFirst.getTeamName() + " won by " + (10 - teamFieldingFirst.getWicketsFallen()) + " wickets.");
//        }else{
//            System.out.println("\nMatch tied.");
//        }
//    }
}
