package com.tekion.dto;

import java.util.ArrayList;

public class ScoreBoard {
    private Team teamBattingFirst , teamFieldingFirst;
    private ArrayList<BallInfo> firstInningTotalBallsInfo , secondInningTotalBallsInfo;
    private int firstInningScore , secondInningScore;
    private int firstInningWickets , secondInningWickets;
    private int firstInningBallsCompleted , secondInningBallsCompleted;

    public ScoreBoard(int matchOvers){
        this.firstInningTotalBallsInfo = new ArrayList<>(matchOvers * 6);
        this.secondInningTotalBallsInfo = new ArrayList<>(matchOvers * 6);
        this.firstInningScore = 0;
        this.secondInningScore = 0;
        this.firstInningWickets = 0;
        this.secondInningWickets = 0;
        this.firstInningBallsCompleted = 0;
        this.secondInningBallsCompleted = 0;
    }

    public void setTeamBattingFirst(Team teamBatting){
        this.teamBattingFirst = teamBatting;
    }

    public void setTeamFieldingFirst(Team teamFielding){
        this.teamFieldingFirst = teamFielding;
    }

    public void updateFirstInningScore(int ballScore){
        Player batsmanOnStrike = teamBattingFirst.getPlayerById(this.teamBattingFirst.getStrikeDetails().getCurrentStrike());
        this.firstInningTotalBallsInfo.set(this.firstInningBallsCompleted , new BallInfo(ballScore , batsmanOnStrike));
        this.firstInningScore += ballScore;
        this.firstInningBallsCompleted++;
    }

    public void fallWicketFirstInning(){
        this.firstInningWickets++;
        this.firstInningBallsCompleted++;
    }

    public void updateSecondInningScore(int ballScore){
        Player batsmanOnStrike = teamFieldingFirst.getPlayerById(this.teamFieldingFirst.getStrikeDetails().getCurrentStrike());
        this.secondInningTotalBallsInfo.set(this.secondInningBallsCompleted , new BallInfo(ballScore , batsmanOnStrike));
        this.secondInningScore += ballScore;
        this.secondInningBallsCompleted++;
    }

    public void fallWicketSecondInning(){
        this.secondInningWickets++;
        this.secondInningBallsCompleted++;
    }

}
