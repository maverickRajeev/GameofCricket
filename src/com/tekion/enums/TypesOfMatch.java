package com.tekion.enums;

public enum TypesOfMatch {
    T20(20),
    ODI(50);

    private final int matchOvers;

    TypesOfMatch(final int matchOvers) {
        this.matchOvers = matchOvers;
    }

    public int getOversForMatchType(){
        return this.matchOvers;
    }
}
