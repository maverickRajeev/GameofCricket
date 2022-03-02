package com.tekion.cricketGame.controller;

import com.tekion.cricketGame.service.CricketMatchService;
import com.tekion.cricketGame.service.CricketMatchServiceImpl;

public class GameController {

    public static void main(String[] args){
        CricketMatchService cricketMatch = new CricketMatchServiceImpl();
        cricketMatch.startCricketMatch();
    }
}
