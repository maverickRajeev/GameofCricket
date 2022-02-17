package com.tekion.service;

import com.tekion.dto.Match;

public class CricketMatchServiceImpl implements CricketMatchService{
    ScoreBoardService scoreBoard = new ScoreBoardServiceImpl();

    public void playCricketMatch(){
        Match cricketGame = new Match();
        cricketGame.playMatch();
    }

}
