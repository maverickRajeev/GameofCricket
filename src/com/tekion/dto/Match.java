package com.tekion.dto;

public class Match {
    private int overs;
    private Team team1 , team2;

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
