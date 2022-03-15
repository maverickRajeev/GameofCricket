package com.tekion.cricketGame.playerService.controller;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.playerService.PlayerService;
import com.tekion.cricketGame.playerService.bean.PlayerBean;
import com.tekion.cricketGame.playerService.bean.PlayerStatsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private CricketMatchService cricketMatchService;

    @GetMapping("/playerInfo/{id}")
    public @ResponseBody ResponseEntity<PlayerBean> getPlayerInfo(@PathVariable("id") int playerId){
        PlayerBean playerInfo = new PlayerBean();
        if(playerService.checkIfPlayerIdExists(playerId)){
            playerInfo = playerService.getPlayerDetails(playerId);
            return ResponseEntity.ok().body(playerInfo);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("playerStat/{playerId}/{matchId}")
    public @ResponseBody ResponseEntity<PlayerStatsBean> getPlayerStats(@PathVariable("playerId") int playerId , @PathVariable("matchId") int matchId){
        PlayerStatsBean playerStat;
        if(playerService.checkIfPlayerIdExists(playerId) && cricketMatchService.checkIfMatchExists(matchId)){
            playerStat = playerService.getPlayerStats(playerId , matchId);
            return ResponseEntity.ok().body(playerStat);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}
