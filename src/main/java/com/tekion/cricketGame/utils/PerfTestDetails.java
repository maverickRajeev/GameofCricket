package com.tekion.cricketGame.utils;

import java.util.Map;

public class PerfTestDetails {

    public static Map<String , Double> getPerfMetricDetails(Map <String , Double> metricsDetails , int taskCount , long[] responseTimes , long totalResponseTime ){
        metricsDetails.put("avgResponseTime" , (double)totalResponseTime/(double) taskCount);
        metricsDetails.put("90thPercentileTime" , MathCalculations.percentileCalculation(responseTimes , 90));
        metricsDetails.put("99thPercentileTime" , MathCalculations.percentileCalculation(responseTimes , 99));
        return metricsDetails;
    }

}
