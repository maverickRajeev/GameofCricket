package com.tekion.cricketGame.utils;

import java.util.Arrays;

public class MathCalculations {

    public static double percentileCalculation(long[] responseTime , double percentile){
        Arrays.sort(responseTime , 0 , responseTime.length);
        int index = (int) Math.ceil(percentile / 100.0 * responseTime.length);
        return responseTime[index - 1];
    }

}
