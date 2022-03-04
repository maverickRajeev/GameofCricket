package com.tekion.cricketGame.cricketSeriesService;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.cricketSeriesService.repo.CricketSeriesRepo;
import com.tekion.cricketGame.enums.TypesOfMatch;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;


public class CricketSeriesServiceImpl implements CricketSeriesService {

    private final CricketSeriesRepo cricketSeriesRepo;
    private final CricketMatchService cricketMatchService;

    @Autowired
    public CricketSeriesServiceImpl(CricketSeriesRepo cricketSeriesRepo , CricketMatchService cricketMatchService){
        this.cricketSeriesRepo = cricketSeriesRepo;
        this.cricketMatchService = cricketMatchService;
    }

    @Override
    public void beginSeries() {
        Scanner sc = new Scanner(System.in);
        int matchOvers = this.chooseSeriesType(sc);
        int numberOfMatches = this.setNumberOfMatches(sc);
        this.startSeries();
        cricketMatch.startCricketMatch();
    }

    private int chooseSeriesType(Scanner sc){
        sc = new Scanner(System.in);
        System.out.println("Please choose match type for the series (T20/0DI): ");
        String userInputSeriesType = sc.nextLine();
        TypesOfMatch matchType = null;
        try {
            matchType = TypesOfMatch.valueOf(userInputSeriesType.toUpperCase());
        }catch (IllegalArgumentException e) {
            System.out.println("Incorrect Match Type.");
            System.exit(0);
        }
        return matchType.getOversForMatchType();;
    }

    private int setNumberOfMatches(Scanner sc){
        System.out.println("Please input number of matches : ");
        int userInputNumberOfMatches = sc.nextInt();
        try{

        }catch{

        }
    }

    private void startSeries(){

    }

}
