package com.tekion.dto;

import static java.lang.Math.max;

public class BattingStatus {
    private int[] strikeHolders;
    private int currentStrike;

    public BattingStatus() {
        this.strikeHolders = new int[2];
        this.strikeHolders[0] = 0;
        this.strikeHolders[1] = 1;
        this.currentStrike = 0;
    }

    public int getCurrentStrike(){
        return this.currentStrike;
    }

    public void changeStrike(){
        this.currentStrike = (currentStrike == this.strikeHolders[0]) ? this.strikeHolders[1] : this.strikeHolders[0];
    }

    public void fallOfWicket(){
        int maxPlayer = max(this.strikeHolders[0] , this.strikeHolders[1]);
        if(this.currentStrike == this.strikeHolders[0]){
            this.strikeHolders[0] = maxPlayer + 1;
        }else{
            this.strikeHolders[1] = maxPlayer + 1;
        }
        this.currentStrike = maxPlayer + 1;
    }

}
