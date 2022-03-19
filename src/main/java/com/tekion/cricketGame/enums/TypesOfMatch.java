package com.tekion.cricketGame.enums;

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

    public static boolean TypeOfMatchExists(String input){
        for(TypesOfMatch choice:values()){
            if(choice.name().equals(input)) {
                return true;
            }
        }
        return false;
    }
}
