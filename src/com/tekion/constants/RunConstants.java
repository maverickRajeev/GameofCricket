package com.tekion.constants;

public final class RunConstants {
    public static final int WICKET = 7;
    public static final int SIX_RUNS = 6;
    public static final int FIVE_RUNS = 5;
    public static final int FOUR_RUNS = 4;
    public static final int DOT_BALL = 0;
    public static final double DOT_CHANCE_T20 = 0.40;
    public static final double RUNS_CHANCE_T20 = DOT_CHANCE_T20 + 0.40;
    public static final double FOUR_RUNS_CHANCE_T20 = RUNS_CHANCE_T20 + 0.09;
    public static final double FIVE_RUNS_CHANCE_T20 = FOUR_RUNS_CHANCE_T20 + 0.01;
    public static final double SIX_RUNS_CHANCE_T20 = FIVE_RUNS_CHANCE_T20 + 0.05;
    public static final double WICKET_CHANCE_T20 = SIX_RUNS_CHANCE_T20 + 0.05;
    public static final double DOT_CHANCE_ODI = 0.58;
    public static final double RUNS_CHANCE_ODI =  DOT_CHANCE_ODI + 0.30;
    public static final double FOUR_RUNS_CHANCE_ODI = RUNS_CHANCE_ODI + 0.06;
    public static final double FIVE_RUNS_CHANCE_ODI = FOUR_RUNS_CHANCE_ODI + 0.005;
    public static final double SIX_RUNS_CHANCE_ODI = FIVE_RUNS_CHANCE_ODI + 0.025;
    public static final double WICKET_CHANCE_ODI = SIX_RUNS_CHANCE_ODI + 0.03;
}
