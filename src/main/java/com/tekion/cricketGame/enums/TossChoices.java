package com.tekion.cricketGame.enums;

public enum TossChoices {
    BAT("Batting"),
    FIELD("Fielding");

    private final String tChoice;

    TossChoices(final String choice) {
        this.tChoice = choice;
    }

    public Boolean choseBatting(){
        return this.tChoice.equals("Batting");
    }
}
