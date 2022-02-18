package com.tekion.service;

import com.tekion.dto.Match;
import com.tekion.dto.ScoreBoard;

public class CricketMatchServiceImpl implements CricketMatchService{
    ScoreBoardService scoreBoardService = new ScoreBoardServiceImpl();

    public void startCricketMatch(){
        Match cricketGame = new Match();
        ScoreBoard scoreBoard = new ScoreBoard();
        this.playMatch(cricketGame , scoreBoard);
    }


    private void playMatch(Match cricketMatch , ScoreBoard scoreBoard){
        cricketMatch.setupMatch(scoreBoard);
        cricketMatch.setTeamInfo();
        cricketMatch.showTeamInfo();
        cricketMatch.coinToss();
        cricketMatch.playFirstInning();
        cricketMatch.inningsBreak();
        cricketMatch.playSecondInning();
        cricketMatch.displayResult();
    }

}
