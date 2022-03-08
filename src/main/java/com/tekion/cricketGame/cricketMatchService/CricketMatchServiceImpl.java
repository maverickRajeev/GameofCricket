package com.tekion.cricketGame.cricketMatchService;

import com.tekion.cricketGame.constants.MatchConstants;
import com.tekion.cricketGame.constants.RunConstants;
import com.tekion.cricketGame.cricketMatchService.dto.CricketMatchDto;
import com.tekion.cricketGame.cricketMatchService.repo.CricketMatchRepo;
import com.tekion.cricketGame.enums.PlayerStatus;
import com.tekion.cricketGame.enums.TossChoices;
import com.tekion.cricketGame.playerService.dto.PlayerDto;
import com.tekion.cricketGame.scoreBoardService.ScoreBoardService;
import com.tekion.cricketGame.scoreBoardService.dto.ScoreBoardDto;
import com.tekion.cricketGame.teamService.TeamService;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import com.tekion.cricketGame.cricketMatchService.utils.MatchCalculationsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CricketMatchServiceImpl implements CricketMatchService {

    private final TeamService teamService;
    private final ScoreBoardService scoreBoardService;
    private final CricketMatchRepo cricketMatchRepo;

    @Autowired
    public CricketMatchServiceImpl(TeamService teamService , ScoreBoardService scoreBoardService , CricketMatchRepo cricketMatchRepo){
        this.teamService = teamService;
        this.scoreBoardService = scoreBoardService;
        this.cricketMatchRepo = cricketMatchRepo;
    }

    public void startCricketMatch(int numberOfOvers , TeamDto team1 , TeamDto team2){
        CricketMatchDto cricketGame = new CricketMatchDto();
        ScoreBoardDto scoreBoard = new ScoreBoardDto(numberOfOvers);
        this.setupMatch(cricketGame , numberOfOvers);
        this.setTeamInfo(cricketGame , team1 , team2);
        this.playMatch(cricketGame , scoreBoard);
    }

    private void playMatch(CricketMatchDto cricketGame , ScoreBoardDto scoreBoard){
        this.coinToss(cricketGame , scoreBoard);
        this.playFirstInning(cricketGame , scoreBoard);
        this.inningsBreak(cricketGame , scoreBoard);
        this.playSecondInning(cricketGame , scoreBoard);
//        this.displayResult();
    }

    private void setupMatch(CricketMatchDto cricketGame , int overs){
             cricketGame.setMatchOvers(overs);
    }

    private void setTeamInfo(CricketMatchDto cricketGame , TeamDto team1 , TeamDto team2) {
        cricketGame.setTeam1(team1);
        cricketGame.setTeam2(team2);
        teamService.setPlayerDetails(cricketGame);
    }

    private void coinToss(CricketMatchDto cricketGame , ScoreBoardDto scoreBoard){
        int tossResult = MatchCalculationsUtils.coinTossResult();
        if (tossResult == 1) {
            chooseBatOrField(scoreBoard , cricketGame.getTeam1() , cricketGame.getTeam2());
        } else {
            chooseBatOrField(scoreBoard , cricketGame.getTeam2() , cricketGame.getTeam1());
        }
    }

    private void chooseBatOrField(ScoreBoardDto scoreBoard , TeamDto tossWinner , TeamDto tossLoser){
        String autoTossChoice = MatchCalculationsUtils.tossChoice() == 1 ? "BAT" : "FIELD";
        TossChoices tossChoice = TossChoices.valueOf(autoTossChoice);

        if(tossChoice.choseBatting()){
            scoreBoardService.setPlayingTeams(scoreBoard , tossWinner , tossLoser);
        }else{
            scoreBoardService.setPlayingTeams(scoreBoard , tossLoser , tossWinner);
        }
    }

    private void playFirstInning(CricketMatchDto cricketGame , ScoreBoardDto scoreBoard){
        System.out.println("\n** Start of 1st inning **");
        this.playInning(cricketGame , scoreBoard , MatchConstants.FIRST_INNING , scoreBoardService.getTeamBattingFirst(scoreBoard));
        scoreBoardService.updateTargetScore(scoreBoard);
        scoreBoardService.getTeamBattingFirst(scoreBoard).displayTeamScorecard();
    }

    private void inningsBreak(CricketMatchDto cricketGame , ScoreBoardDto scoreBoard){
        System.out.println("\n** Innings break **");
        scoreBoardService.displayScoreBoard(scoreBoard , MatchConstants.FIRST_INNING);
        System.out.println("Target : " + scoreBoardService.getTargetScore(scoreBoard));
        System.out.println(scoreBoardService.getTeamFieldingFirst(scoreBoard).getTeamName() + " need " + scoreBoardService.getTargetScore(scoreBoard) + " runs in " + cricketGame.getMatchOvers() * 6 + " balls.");
    }

    private void playSecondInning(CricketMatchDto cricketGame , ScoreBoardDto scoreBoard){
        System.out.println("\n** Start of 2nd inning **");
        this.playInning( cricketGame , scoreBoard , MatchConstants.SECOND_INNING , scoreBoardService.getTeamFieldingFirst(scoreBoard));
        scoreBoardService.getTeamFieldingFirst(scoreBoard).displayTeamScorecard();
    }

    private void playInning(CricketMatchDto cricketGame , ScoreBoardDto scoreBoard , int inning , TeamDto teamBatting){
        for(int i = 0 ; i < cricketGame.getMatchOvers() ; i++){
            System.out.println("\nOver : " + (i+1));
            playOver(scoreBoard , inning , cricketGame.getMatchOvers() , teamBatting);
            scoreBoardService.displayScoreBoard(scoreBoard , inning);
            if(inning == MatchConstants.SECOND_INNING && scoreBoardService.getTeamScore(scoreBoard , MatchConstants.SECOND_INNING) >= scoreBoardService.getTargetScore(scoreBoard) )
                break;
            if(scoreBoardService.getTeamWickets(scoreBoard , inning) == 10)
                break;
        }
    }

    private void playOver(ScoreBoardDto scoreBoard , int inning , int matchOvers , TeamDto teamBatting){
        int ballScore;
        PlayerDto currentBatsMan;
        for(int ball = 0 ; ball < 6 ; ball++){
            currentBatsMan = teamBatting.getCurrentBatsMan();
            ballScore = playBall(matchOvers , currentBatsMan);
            scoreBoardService.updateScore(scoreBoard , ballScore , inning);
            PlayerStatUpdateAndStrikeChange(scoreBoard , inning ,ballScore , currentBatsMan , teamBatting);
            if(inning == MatchConstants.SECOND_INNING && scoreBoardService.getTeamScore(scoreBoard , MatchConstants.SECOND_INNING) >= scoreBoardService.getTargetScore(scoreBoard) )
                break;
            if(scoreBoardService.getTeamWickets(scoreBoard , inning) == 10)
                break;
        }
        teamBatting.changeTeamStrike();
    }

    private int playBall(int matchOvers , PlayerDto currentBatsman) {
        int runScored = MatchCalculationsUtils.eachBallScore(matchOvers, currentBatsman);
        return runScored;
    }

    private void PlayerStatUpdateAndStrikeChange(ScoreBoardDto scoreBoard , int inning , int runScored , PlayerDto currentBatsman , TeamDto teamBatting ){

        if( runScored == RunConstants.WICKET){
            currentBatsman.increaseBallsPlayed();
            currentBatsman.updatePlayerStatus(PlayerStatus.OUT);
            if(scoreBoardService.getTeamWickets(scoreBoard , inning) < 10)
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
