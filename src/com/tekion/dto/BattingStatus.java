package com.tekion.dto;

import java.util.Arrays;
import static java.util.Collections.swap;

public class BattingStatus {
    private int[] StrikeHolders;
    private int currentStrike;


    public BattingStatus() {
        this.StrikeHolders = new int[2];
        this.StrikeHolders[0] = 0;
        this.StrikeHolders[1] = 1;
        currentStrike = 0;
    }

    public void changeOvers(){
        swap(Arrays.asList(StrikeHolders) , 0 , 1);
    }

}
