package com.tekion.cricketGame.cricketMatchService;

import com.tekion.cricketGame.constants.MatchConstants;
import com.tekion.cricketGame.constants.RunConstants;
import com.tekion.cricketGame.cricketMatchService.dto.MatchDto;
import com.tekion.cricketGame.playerService.dto.PlayerDto;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import com.tekion.cricketGame.enums.PlayerStatus;
import com.tekion.cricketGame.enums.TossChoices;
import com.tekion.cricketGame.enums.TypesOfMatch;
import com.tekion.cricketGame.cricketMatchService.repo.MatchRepository;
import com.tekion.cricketGame.cricketMatchService.repo.MatchRepositoryImpl;
import com.tekion.cricketGame.scoreBoardService.ScoreBoardService;
import com.tekion.cricketGame.scoreBoardService.ScoreBoardServiceImpl;
import com.tekion.cricketGame.teamService.TeamService;
import com.tekion.cricketGame.teamService.TeamServiceImpl;
import com.tekion.cricketGame.utils.MatchCalculationsUtils;

import java.util.Scanner;

public class CricketMatchServiceImpl implements CricketMatchService {
    MatchDto cricketGame = new MatchDto();
    TeamService teamService = new TeamServiceImpl();
    ScoreBoardService scoreBoardService = new ScoreBoardServiceImpl();
    MatchRepository matchRepo = new MatchRepositoryImpl();


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
//        this.displayResult();
    }

    private void setupMatch(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Please choose match type (T20/0DI): ");
        String userInputMatchType = sc.nextLine();
        try {
             TypesOfMatch matchType = TypesOfMatch.valueOf(userInputMatchType.toUpperCase());
             cricketGame.setMatchOvers(matchType.getOversForMatchType());
             scoreBoardService.setScoreBoard(cricketGame.getMatchOvers());
        }catch (IllegalArgumentException e) {
            System.out.println("Incorrect Match Type.");
            System.exit(0);
        }
    }

    private void setTeamInfo() {
        cricketGame.setTeam1(new TeamDto(teamService.inputTeam1Details()));
        cricketGame.setTeam2(new TeamDto(teamService.inputTeam2Details()));
        teamService.setPlayerDetails(cricketGame);
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

    private void chooseBatOrField(TeamDto tossWinner , TeamDto tossLoser){
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

    private void playFirstInning(){
        System.out.println("\n** Start of 1st inning **");
        this.playInning(MatchConstants.FIRST_INNING , scoreBoardService.getTeamBattingFirst());
        scoreBoardService.updateTargetScore();
        scoreBoardService.getTeamBattingFirst().displayTeamScorecard();
    }

    private void inningsBreak(){
        System.out.println("\n** Innings break **");
        scoreBoardService.displayScoreBoard(MatchConstants.FIRST_INNING);
        System.out.println("Target : " + scoreBoardService.getTargetScore());
        System.out.println(scoreBoardService.getTeamFieldingFirst().getTeamName() + " need " + scoreBoardService.getTargetScore() + " runs in " + cricketGame.getMatchOvers() * 6 + " balls.");
    }

    private void playSecondInning(){
        System.out.println("\n** Start of 2nd inning **");
        this.playInning(MatchConstants.SECOND_INNING , scoreBoardService.getTeamFieldingFirst());
        scoreBoardService.getTeamFieldingFirst().displayTeamScorecard();
    }

    private void playInning(int inning , TeamDto teamBatting){
        for(int i = 0 ; i < cricketGame.getMatchOvers() ; i++){
            System.out.println("\nOver : " + (i+1));
            playOver(inning , cricketGame.getMatchOvers() , teamBatting);
            scoreBoardService.displayScoreBoard(inning);
            if(inning == MatchConstants.SECOND_INNING && scoreBoardService.getTeamScore(MatchConstants.SECOND_INNING) >= scoreBoardService.getTargetScore() )
                break;
            if(scoreBoardService.getTeamWickets(inning) == 10)
                break;
        }
    }

    private void playOver(int inning , int matchOvers , TeamDto teamBatting){
        int ballScore;
        PlayerDto currentBatsMan;
        for(int ball = 0 ; ball < 6 ; ball++){
            currentBatsMan = teamBatting.getCurrentBatsMan();
            ballScore = playBall(matchOvers , currentBatsMan);
            scoreBoardService.updateScore(ballScore , inning);
            PlayerStatUpdateAndStrikeChange(inning ,ballScore , currentBatsMan , teamBatting);
            if(inning == MatchConstants.SECOND_INNING && scoreBoardService.getTeamScore(MatchConstants.SECOND_INNING) >= scoreBoardService.getTargetScore() )
                break;
            if(scoreBoardService.getTeamWickets(inning) == 10)
                break;
        }
        teamBatting.changeTeamStrike();
//
//        this.displayScore(team);
//        if(inning == 2 && team.getTeamScore() < this.targetScore){
//            System.out.println(teamFieldingFirst.getTeamName() + " need " + (this.targetScore-teamFieldingFirst.getTeamScore()) + " runs in " + (this.overs * 6 - teamFieldingFirst.getTotalBallsPlayed()) + " balls.");
//        }
    }

    private int playBall(int matchOvers , PlayerDto currentBatsman) {
        int runScored = MatchCalculationsUtils.eachBallScore(matchOvers, currentBatsman);
        return runScored;
    }

    private void PlayerStatUpdateAndStrikeChange(int inning , int runScored , PlayerDto currentBatsman , TeamDto teamBatting ){

        if( runScored == RunConstants.WICKET){
            currentBatsman.increaseBallsPlayed();
            currentBatsman.updatePlayerStatus(PlayerStatus.OUT);
            if(scoreBoardService.getTeamWickets(inning) < 10)
               teamBatting.newPlayerChange();
        }else{
            currentBatsman.updatePlayerStatus(PlayerStatus.NOT_OUT);
            currentBatsman.increaseScore(runScored);
            currentBatsman.increaseBallsPlayed();
            if(runScored % 2 == 1){
                teamBatting.changeTeamStrike();
            }
        }
    }

}
