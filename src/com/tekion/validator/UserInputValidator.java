package com.tekion.validator;

public class UserInputValidator {

    public static Boolean matchInputValidator(String userInput){
        return userInput.equals("T20") || userInput.equals("ODI");
    }

    public static Boolean tossChoiceValidator(String userInput){
        return userInput.equals("BAT") || userInput.equals("BOWL");
    }
}
