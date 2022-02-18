package com.tekion.controller;

import com.tekion.service.GameService;

public class GameController {

    public static void main(String[] args){
        GameService game = new GameService();
        game.playCricketMatch();
    }
}
