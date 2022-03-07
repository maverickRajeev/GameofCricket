package com.tekion.cricketGame.scoreBoardService.dto;

import com.tekion.cricketGame.constants.RunConstants;
import com.tekion.cricketGame.playerService.dto.PlayerDto;

public class BallInfoDto {
     private int runScored;
     private Boolean wicketFell;
     private PlayerDto batsmanOnStrike;
     private PlayerDto bowlerBowling;

     public BallInfoDto(int runScored , PlayerDto batsman) {
         this.setBatsmanOnStrike(batsman);
         if(runScored != RunConstants.WICKET){
             this.runScored = runScored;
             this.wicketFell = false;
         } else {
             this.wicketFell = true;
             this.runScored = 0;
         }

     }

     public int getRunScored() {
        return this.runScored;
     }

     public Boolean ifWicketFell() {
        return wicketFell;
     }

     public PlayerDto getBatsmanOnStrike() {
        return batsmanOnStrike;
     }

     public PlayerDto getBowlerBowling() {
        return bowlerBowling;
     }

     private void setBatsmanOnStrike(PlayerDto batsman){
        this.batsmanOnStrike = batsman;
     }

     private void setBowlerBowling(PlayerDto bowler) {
        this.bowlerBowling = bowlerBowling;
     }


}
