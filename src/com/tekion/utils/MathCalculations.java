package com.tekion.utils;

import java.util.concurrent.ThreadLocalRandom;
import com.tekion.constants.RunConstants;

public class MathCalculations {

    public static int eachBallScoreT20(){
        double chance = Math.random();
        if(chance < RunConstants.DOT_CHANCE_T20)
            return RunConstants.DOT_BALL;
        else if(chance < RunConstants.RUNS_CHANCE_T20)
            return ThreadLocalRandom.current().nextInt(1 , 4);
        else if(chance < RunConstants.FOUR_RUNS_CHANCE_T20)
            return RunConstants.FOUR_RUNS;
        else if(chance < RunConstants.FIVE_RUNS_CHANCE_T20)
            return RunConstants.FIVE_RUNS;
        else if(chance < RunConstants.SIX_RUNS_CHANCE_T20)
            return RunConstants.SIX_RUNS;
        else
            return RunConstants.WICKET;
    }

    public static int eachBallScoreODI(){
        double chance = Math.random();
        if(chance < RunConstants.DOT_CHANCE_ODI)
            return RunConstants.DOT_BALL;
        else if(chance < RunConstants.RUNS_CHANCE_ODI)
            return ThreadLocalRandom.current().nextInt(1 , 4);
        else if(chance < RunConstants.FOUR_RUNS_CHANCE_ODI)
            return RunConstants.FOUR_RUNS;
        else if(chance < RunConstants.FIVE_RUNS_CHANCE_ODI)
            return RunConstants.FIVE_RUNS;
        else if(chance < RunConstants.SIX_RUNS_CHANCE_ODI)
            return RunConstants.SIX_RUNS;
        else
            return RunConstants.WICKET;
    }

}
