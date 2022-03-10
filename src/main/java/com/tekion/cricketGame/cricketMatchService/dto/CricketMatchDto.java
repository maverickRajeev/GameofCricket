package com.tekion.cricketGame.cricketMatchService.dto;

import com.tekion.cricketGame.teamService.dto.TeamDto;

public class CricketMatchDto {
    private int overs;
    private TeamDto team1 , team2;
    private TeamDto winnerTeam;

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

    public void setWinnerTeam(TeamDto team){
        this.winnerTeam = team;
    }

    public TeamDto getWinnerTeam() {
        return this.winnerTeam;
    }
}
