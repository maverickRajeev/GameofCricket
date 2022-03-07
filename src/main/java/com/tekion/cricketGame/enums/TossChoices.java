package com.tekion.cricketGame.enums;

public enum TossChoices {
    BAT("BAT"),
    FIELD("FIELD");

    private final String tChoice;

    TossChoices(final String choice) {
        this.tChoice = choice;
    }

    public Boolean choseBatting(){
        return this.tChoice.equals("BAT");
    }
}
