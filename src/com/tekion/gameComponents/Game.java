package com.tekion.gameComponents;

import java.util.Scanner;
import java.util.Random;

public class Game {
    private final int overs;
    private Team team1 , team2;

    public Game(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter number of overs: ");
        this.overs = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter name of Team-1 : ");
        this.team1 = new Team(sc.nextLine() ,this.overs * 6);
        System.out.println("Enter name of Team-2 : ");
        this.team2 = new Team(sc.nextLine() ,this.overs * 6);
    }

    private int coinToss(){
        return new Random().nextBoolean() ? 1 : 2;
    }

    private void playOver(Team team){
        // numbers 0-6 represent runs , 7 represent wicket
        for(int i = 0 ; i < 6 ; i++){
            int ball = new Random().nextInt(8);
            team.decreaseAvailableBalls();
            if(ball == 7){
                team.fallWicket();
                System.out.print("W ");
            }else{
                team.increaseTeamScore(ball);
                System.out.print(ball + " ");
            }
        }
        System.out.println("\nScore of " + team.getTeamName() + ": " + team.getTeamScore() + "/" + team.getWicketsFallen());
    }

    private void firstInning(Team team){
        for(int i = 0 ; i < overs ; i++){
            System.out.println("Over : " + (i+1));
            playOver(team);
            if(team.getWicketsFallen() == 10)
                break;
        }

    }

    private void secondInning(Team team , int target){
        for(int i = 0 ; i < overs ; i++){
            System.out.println("Over : " + (i+1));
            playOver(team);
            if(team.getTeamScore() >= target)
                break;
            if(team.getWicketsFallen() == 10)
                break;
        }
    }

    public void playGame(){
        int tossWinner = coinToss();
        System.out.println("Team-" + tossWinner + " won the toss. Please type 1 for batting and 2 for bowling : ");
        Scanner sc = new Scanner(System.in);
        int teamChoice = sc.nextInt();
        int target;
        if(tossWinner == 1 && teamChoice == 1) {
            firstInning(team1);
            target = team1.getTeamScore();
            secondInning(team2 , target);
        }
        else if(tossWinner == 1 && teamChoice == 2) {
            firstInning(team2);
            target = team2.getTeamScore();
            secondInning(team1 , target);
        }
        else if(tossWinner == 2 && teamChoice == 1) {
            firstInning(team2);
            target = team2.getTeamScore();
            secondInning(team1 , target);
        }
        else{
            firstInning(team1);
            target = team1.getTeamScore();
            secondInning(team2 , target);
        }
    }
}
