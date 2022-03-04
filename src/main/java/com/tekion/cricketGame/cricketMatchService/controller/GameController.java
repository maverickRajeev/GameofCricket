package com.tekion.cricketGame.cricketMatchService.controller;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.cricketMatchService.CricketMatchServiceImpl;
import org.ariia.mvc.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(value = "/startGame")
public class GameController {
    public static void main(String[] args){
        CricketMatchService cricketMatch = new CricketMatchServiceImpl();
        cricketMatch.startCricketMatch();
    }

    @GetMapping("/startMatch")
    public void startMatch(){

    }
}
