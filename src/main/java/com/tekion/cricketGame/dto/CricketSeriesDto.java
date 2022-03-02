package com.tekion.cricketGame.dto;

public class CricketSeriesDto {
    private int numberOfOvers;
    private int numberOfMatches;
    private TeamDto team1 , team2;
    private int numberOfMatchesWonByTeam1;
    private int numberOfMatchesWonByTeam2;
    private int numberOfMatchesTied;
    private TeamDto seriesWinningTeam;

    public CricketSeriesDto(int matchOvers , int numberOfMatches){
        this.numberOfOvers = matchOvers;
        this.numberOfMatches = numberOfMatches;
        this.numberOfMatchesWonByTeam1 = 0;
        this.numberOfMatchesWonByTeam2 = 0;
        this.numberOfMatchesTied = 0;
    }

    public int getNumberOfOvers() {
        return this.numberOfOvers;
    }

    public int getNumberOfMatches() {
        return this.numberOfMatches;
    }

    public void setSeriesTeam1(TeamDto team){
        this.team1 = team;
    }

    public void setSeriesTeam2(TeamDto team){
        this.team2 = team;
    }

    public TeamDto getSeriesTeam1(){
        return this.team1;
    }

    public TeamDto getSeriesTeam2(){
        return this.team2;
    }

    public void setNumberOfMatchesWonByTeam1(){
        this.numberOfMatchesWonByTeam1++;
    }

    public int getNumberOfMatchesWonByTeam1(){
        return this.numberOfMatchesWonByTeam1;
    }

    public void setNumberOfMatchesWonByTeam2(){
        this.numberOfMatchesWonByTeam2++;
    }

    public int getNumberOfMatchesWonByTeam2(){
        return this.numberOfMatchesWonByTeam2;
    }

    public void setNumberOfMatchesTied(){
        this.numberOfMatchesTied++;
    }

    public int getNumberOfMatchesTied(){
        return this.numberOfMatchesTied;
    }

    public void setSeriesWinningTeam(TeamDto team){
        this.seriesWinningTeam = team;
    }

    public TeamDto getSeriesWinningTeam(TeamDto team){
        return this.seriesWinningTeam;
    }
}
