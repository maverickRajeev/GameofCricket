package com.tekion.service;

import com.tekion.dto.Match;

public class GameService {

    public void playCricketMatch(){
        Match cricketGame = new Match();
        cricketGame.playMatch();
    }
}
