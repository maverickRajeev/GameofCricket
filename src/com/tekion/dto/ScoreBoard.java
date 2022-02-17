package com.tekion.dto;

import java.util.ArrayList;

public class ScoreBoard {
    private Team teamBattingFirst , teamFieldingFirst;
    private ArrayList<BallInfo> firstInningTotalBalls , secondInningTotalBalls;
    private int firstInningScore , secondInningScore;
    private int firstInningWickets , secondInningWickets;
    private int firstInningBallsCompleted , secondInningBallsCompleted;

    public ScoreBoard(int matchOvers){
        this.firstInningTotalBalls = new ArrayList<>(matchOvers * 6);
        this.secondInningTotalBalls = new ArrayList<>(matchOvers * 6);
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
        this.firstInningScore += ballScore;
        this.firstInningBallsCompleted++;
    }

    public void fallWicketFirstInning(){
        this.firstInningWickets++;
        this.secondInningBallsCompleted++;
    }

    public void updateSecondInningScore(int ballScore){
        this.secondInningScore += ballScore;
    }

    public void fallWicketSecondInning(){
        this.secondInningWickets++;
    }

}
