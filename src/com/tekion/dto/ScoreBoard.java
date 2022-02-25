package com.tekion.dto;

import com.tekion.constants.RunConstants;

import java.util.ArrayList;

public class ScoreBoard {
    private Team teamBattingFirst , teamFieldingFirst;
    private ArrayList<BallInfo> firstInningTotalBallsInfo , secondInningTotalBallsInfo;
    private int firstInningScore , secondInningScore;
    private int firstInningWickets , secondInningWickets;
    private int firstInningBallsCompleted , secondInningBallsCompleted;
    private int targetScore;

    public ScoreBoard(int matchOvers){
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

    public void setTeamBattingFirst(Team teamBatting){
        this.teamBattingFirst = teamBatting;
    }

    public void setTeamFieldingFirst(Team teamFielding){
        this.teamFieldingFirst = teamFielding;
    }

    public Team getTeamBattingFirst(){
        return this.teamBattingFirst;
    }

    public Team getTeamFieldingFirst(){
        return this.teamFieldingFirst;
    }

    public void updateFirstInningScore(int ballScore){
        Player batsmanOnStrike = this.teamBattingFirst.getCurrentBatsMan();
        this.firstInningTotalBallsInfo.add(this.firstInningBallsCompleted , new BallInfo(ballScore , batsmanOnStrike));
        this.firstInningScore += ballScore;
        this.firstInningBallsCompleted++;
    }

    public void fallWicketFirstInning(){
        Player batsmanOnStrike = this.teamBattingFirst.getCurrentBatsMan();
        this.firstInningTotalBallsInfo.add(this.firstInningBallsCompleted , new BallInfo(RunConstants.WICKET , batsmanOnStrike));
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
        Player batsmanOnStrike = this.teamFieldingFirst.getCurrentBatsMan();
        this.secondInningTotalBallsInfo.add(this.secondInningBallsCompleted , new BallInfo(ballScore , batsmanOnStrike));
        this.secondInningScore += ballScore;
        this.secondInningBallsCompleted++;
    }

    public void fallWicketSecondInning(){
        Player batsmanOnStrike = this.teamFieldingFirst.getCurrentBatsMan();
        this.secondInningTotalBallsInfo.add(this.secondInningBallsCompleted , new BallInfo(RunConstants.WICKET , batsmanOnStrike));
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
