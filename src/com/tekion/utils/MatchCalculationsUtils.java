package com.tekion.utils;

import java.util.concurrent.ThreadLocalRandom;
import com.tekion.constants.RunConstants;
import com.tekion.dto.Player;
import com.tekion.enums.PlayerRoles;

public class MatchCalculationsUtils {

    public static int coinTossResult(){
        return ThreadLocalRandom.current().nextInt(1,3);
    }

    public static int eachBallScore(int matchOvers , Player batsMan){
          if(matchOvers == 20){
              return eachBallScoreT20(batsMan);
          }else{
              return eachBallScoreODI(batsMan);
          }
    }

    private static int eachBallScoreT20(Player batsMan){
        if ((batsMan.getPlayerRole() == PlayerRoles.BATSMAN) || (batsMan.getPlayerRole() == PlayerRoles.ALLROUNDER)){
            return eachBallScoreT20BatsmanAllRounder();
        }else{
            return eachBallScoreT20Bowler();
        }
    }

    private static int eachBallScoreODI(Player batsMan){
        if((batsMan.getPlayerRole() == PlayerRoles.BATSMAN) || (batsMan.getPlayerRole() == PlayerRoles.ALLROUNDER)) {
            return eachBallScoreODIBatsmanAllRounder();
        }else {
            return eachBallScoreODIBowler();
        }
    }

    private static int eachBallScoreT20BatsmanAllRounder(){
        double chance = Math.random();
        if(chance < RunConstants.DOT_CHANCE_T20_BATSMAN_ALLROUNDER)
            return RunConstants.DOT_BALL;
        else if(chance < RunConstants.RUNS_CHANCE_T20_BATSMAN_ALLROUNDER)
            return ThreadLocalRandom.current().nextInt(1 , 4);
        else if(chance < RunConstants.FOUR_RUNS_CHANCE_T20_BATSMAN_ALLROUNDER)
            return RunConstants.FOUR_RUNS;
        else if(chance < RunConstants.FIVE_RUNS_CHANCE_T20_BATSMAN_ALLROUNDER)
            return RunConstants.FIVE_RUNS;
        else if(chance < RunConstants.SIX_RUNS_CHANCE_T20_BATSMAN_ALLROUNDER)
            return RunConstants.SIX_RUNS;
        else
            return RunConstants.WICKET;
    }

    private static int eachBallScoreT20Bowler(){
        double chance = Math.random();
        if(chance < RunConstants.DOT_CHANCE_T20_BOWLER)
            return RunConstants.DOT_BALL;
        else if(chance < RunConstants.RUNS_CHANCE_T20_BOWLER)
            return ThreadLocalRandom.current().nextInt(1 , 4);
        else if(chance < RunConstants.FOUR_RUNS_CHANCE_T20_BOWLER)
            return RunConstants.FOUR_RUNS;
        else if(chance < RunConstants.FIVE_RUNS_CHANCE_T20_BOWLER)
            return RunConstants.FIVE_RUNS;
        else if(chance < RunConstants.SIX_RUNS_CHANCE_T20_BOWLER)
            return RunConstants.SIX_RUNS;
        else
            return RunConstants.WICKET;
    }

    private static int eachBallScoreODIBatsmanAllRounder(){
        double chance = Math.random();
        if(chance < RunConstants.DOT_CHANCE_ODI_BATSMAN_ALLROUNDER)
            return RunConstants.DOT_BALL;
        else if(chance < RunConstants.RUNS_CHANCE_ODI_BATSMAN_ALLROUNDER)
            return ThreadLocalRandom.current().nextInt(1 , 4);
        else if(chance < RunConstants.FOUR_RUNS_CHANCE_ODI_BATSMAN_ALLROUNDER)
            return RunConstants.FOUR_RUNS;
        else if(chance < RunConstants.FIVE_RUNS_CHANCE_ODI_BATSMAN_ALLROUNDER)
            return RunConstants.FIVE_RUNS;
        else if(chance < RunConstants.SIX_RUNS_CHANCE_ODI_BATSMAN_ALLROUNDER)
            return RunConstants.SIX_RUNS;
        else
            return RunConstants.WICKET;
    }

    private static int eachBallScoreODIBowler(){
        double chance = Math.random();
        if(chance < RunConstants.DOT_CHANCE_ODI_BOWLER)
            return RunConstants.DOT_BALL;
        else if(chance < RunConstants.RUNS_CHANCE_ODI_BOWLER)
            return ThreadLocalRandom.current().nextInt(1 , 4);
        else if(chance < RunConstants.FOUR_RUNS_CHANCE_ODI_BOWLER)
            return RunConstants.FOUR_RUNS;
        else if(chance < RunConstants.FIVE_RUNS_CHANCE_ODI_BOWLER)
            return RunConstants.FIVE_RUNS;
        else if(chance < RunConstants.SIX_RUNS_CHANCE_ODI_BOWLER)
            return RunConstants.SIX_RUNS;
        else
            return RunConstants.WICKET;
    }

}
