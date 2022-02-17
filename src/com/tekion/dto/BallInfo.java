package com.tekion.dto;

import com.tekion.constants.RunConstants;

public class BallInfo {
     private int runScored;
     private Boolean wicketFell;
     private Player batsmanOnStrike;
     private Player bowlerBowling;

     public BallInfo(int runScored) {
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

     public void setBatsmanOnStrike(Player batsman){
         this.batsmanOnStrike = batsman;
     }

     public Player getBatsmanOnStrike() {
        return batsmanOnStrike;
     }

     public void setBowlerBowling(Player bowler) {
        this.bowlerBowling = bowlerBowling;
     }

     public Player getBowlerBowling() {
        return bowlerBowling;
     }
}
