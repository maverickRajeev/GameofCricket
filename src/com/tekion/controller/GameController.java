package com.tekion.controller;

import com.tekion.service.CricketMatchService;
import com.tekion.service.CricketMatchServiceImpl;


public class GameController {

    public static void main(String[] args){
        CricketMatchService cricketMatch = new CricketMatchServiceImpl();
        cricketMatch.startCricketMatch();
    }
}
