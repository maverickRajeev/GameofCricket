package com.tekion.cricketGame.scoreBoardService.dto;

import com.tekion.cricketGame.constants.RunConstants;
import com.tekion.cricketGame.dto.BallInfoDto;
import com.tekion.cricketGame.playerService.dto.PlayerDto;
import com.tekion.cricketGame.teamService.dto.TeamDto;

import java.util.ArrayList;

public class ScoreBoardDto {
    private TeamDto teamBattingFirst , teamFieldingFirst;
    private ArrayList<BallInfoDto> firstInningTotalBallsInfo , secondInningTotalBallsInfo;
    private int firstInningScore , secondInningScore;
    private int firstInningWickets , secondInningWickets;
    private int firstInningBallsCompleted , secondInningBallsCompleted;
    private int targetScore;

    public ScoreBoardDto (int matchOvers){
        this.firstInningScore = 0;
        this.secondInningScore = 0;
        this.firstInningWickets = 0;
        this.secondInningWickets = 0;
        this.firstInningBallsCompleted = 0;
        this.secondInningBallsCompleted = 0;
        this.targetScore = 0;
        this.firstInningTotalBallsInfo = new ArrayList<>(matchOvers * 6);
        this.secondInningTotalBallsInfo = new ArrayList<>(matchOvers * 6);
    }

    public void setTeamBattingFirst(TeamDto teamBatting){
        this.teamBattingFirst = teamBatting;
    }

    public void setTeamFieldingFirst(TeamDto teamFielding){
        this.teamFieldingFirst = teamFielding;
    }

    public TeamDto getTeamBattingFirst(){
        return this.teamBattingFirst;
    }

    public TeamDto getTeamFieldingFirst(){
        return this.teamFieldingFirst;
    }

    public void updateFirstInningScore(int ballScore){
        PlayerDto batsmanOnStrike = this.teamBattingFirst.getCurrentBatsMan();
        this.firstInningTotalBallsInfo.add(this.firstInningBallsCompleted , new BallInfoDto(ballScore , batsmanOnStrike));
        this.firstInningScore += ballScore;
        this.firstInningBallsCompleted++;
    }

    public void fallWicketFirstInning(){
        PlayerDto batsmanOnStrike = this.teamBattingFirst.getCurrentBatsMan();
        this.firstInningTotalBallsInfo.add(this.firstInningBallsCompleted , new BallInfoDto(RunConstants.WICKET , batsmanOnStrike));
        this.firstInningWickets++;
        this.firstInningBallsCompleted++;
    }

    public int getFirstInningScore(){
        return this.firstInningScore;
    }

    public int getFirstInningWickets(){
        return this.firstInningWickets;
    }

    public int getFirstInningBallsCompleted(){
        return this.firstInningBallsCompleted;
    }

    public void setTargetScore(){
        this.targetScore = this.firstInningScore;
    }

    public int getTargetScore(){
        return this.targetScore;
    }

    public void updateSecondInningScore(int ballScore){
        PlayerDto batsmanOnStrike = this.teamFieldingFirst.getCurrentBatsMan();
        this.secondInningTotalBallsInfo.add(this.secondInningBallsCompleted , new BallInfoDto(ballScore , batsmanOnStrike));
        this.secondInningScore += ballScore;
        this.secondInningBallsCompleted++;
    }

    public void fallWicketSecondInning(){
        PlayerDto batsmanOnStrike = this.teamFieldingFirst.getCurrentBatsMan();
        this.secondInningTotalBallsInfo.add(this.secondInningBallsCompleted , new BallInfoDto(RunConstants.WICKET , batsmanOnStrike));
        this.secondInningWickets++;
        this.secondInningBallsCompleted++;
    }

    public int getSecondInningScore(){
        return this.secondInningScore;
    }

    public int getSecondInningWickets(){
        return this.secondInningWickets;
    }

    public int getSecondInningBallsCompleted(){
        return this.secondInningBallsCompleted;
    }

}
