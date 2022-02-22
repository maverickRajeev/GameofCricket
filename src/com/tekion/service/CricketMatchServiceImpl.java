package com.tekion.service;

import com.tekion.bean.MatchBean;
import com.tekion.bean.MatchBeanImpl;
import com.tekion.dto.Match;
import com.tekion.dto.Team;
import com.tekion.enums.TossChoices;
import com.tekion.enums.TypesOfMatch;
import com.tekion.utils.MatchCalculationsUtils;

import java.util.Scanner;

public class CricketMatchServiceImpl implements CricketMatchService{
    Match cricketGame = new Match();
    ScoreBoardService scoreBoardService = new ScoreBoardServiceImpl();
    MatchBean matchData = new MatchBeanImpl();

    public void startCricketMatch(){
        this.setupMatch();
        this.setTeamInfo();
        this.showTeamInfo();
        this.playMatch();
    }

    private void playMatch(){
        this.coinToss();
        this.playFirstInning();
        this.inningsBreak();
        this.playSecondInning();
        this.displayResult();
    }

    private void setupMatch(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose match type (T20/0DI): ");
        String userInputMatchType = sc.nextLine();
        try {
             TypesOfMatch matchType = TypesOfMatch.valueOf(userInputMatchType.toUpperCase());
             cricketGame.setMatchOvers(matchType.getOversForMatchType());
        }catch (IllegalArgumentException e) {
            System.out.println("Incorrect Match Type.");
            System.exit(0);
        }
    }

    private void setTeamInfo(){
        Scanner sc = new Scanner(System.in);
        System.out.println("\nEnter name of Team-1 : ");
        cricketGame.setTeam1(new Team(sc.nextLine()));
        System.out.println("Enter name of Team-2 : ");
        cricketGame.setTeam2(new Team(sc.nextLine()));
        System.out.println("Use default names and player roles(Y/N) ? : ");
        char userInputForDefaultPlayersList = sc.next().toUpperCase().charAt(0);
        if(userInputForDefaultPlayersList == 'Y'){
            cricketGame.getTeam1().setDefaultPlayersList();
            cricketGame.getTeam2().setDefaultPlayersList();
        }else if(userInputForDefaultPlayersList == 'N') {
            cricketGame.getTeam1().setPlayersList();
            cricketGame.getTeam2().setPlayersList();
        }else{
            System.out.println("Incorrect choice.");
            System.exit(0);
        }
    }

    private void showTeamInfo(){
        System.out.println("\n** Team-1 List **");
        cricketGame.getTeam1().displayPlayersList();
        System.out.println("\n** Team-2 List **");
        cricketGame.getTeam2().displayPlayersList();
    }

    private void coinToss(){
        System.out.println("\nLet's have a coin toss.");
        int tossResult = MatchCalculationsUtils.coinTossResult();
        if (tossResult == 1) {
            System.out.println(cricketGame.getTeam1().getTeamName() + " won the toss. Please choose (BAT/FIELD).");
            chooseBatOrField(cricketGame.getTeam1() , cricketGame.getTeam2());
        } else {
            System.out.println(cricketGame.getTeam2().getTeamName() + " won the toss. Please choose (BAT/FIELD).");
            chooseBatOrField(cricketGame.getTeam2() , cricketGame.getTeam1());
        }
    }

    private void chooseBatOrField(Team tossWinner , Team tossLoser){
        Scanner sc = new Scanner(System.in);
        String userInput = sc.nextLine();
        try {
            TossChoices tossChoice = TossChoices.valueOf(userInput.toUpperCase());
            if(tossChoice.choseBatting()){
                scoreBoardService.setPlayingTeams(tossWinner , tossLoser);
            }else{
                scoreBoardService.setPlayingTeams(tossLoser , tossWinner);
            }
            System.out.println(tossWinner.getTeamName() + " chose to " + tossChoice + " first." );
        }catch (IllegalArgumentException e){
            System.out.println("Incorrect choice made.");
            System.exit(0);
        }
    }

}
