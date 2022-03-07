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

    public void startCricketMatch(int numberOfOvers , int numberOfMatches , String team1Name , String team2Name){
        CricketMatchDto cricketGame = new CricketMatchDto();
        ScoreBoardDto scoreBoard = new ScoreBoardDto(numberOfOvers);
        this.setupMatch(cricketGame , numberOfOvers);
        this.setTeamInfo(cricketGame , team1Name , team2Name);
        this.playMatch(cricketGame , scoreBoard);
    }

    private void playMatch(CricketMatchDto cricketGame , ScoreBoardDto scoreBoard){
        this.coinToss(cricketGame);
        this.playFirstInning(cricketGame);
        this.inningsBreak(cricketGame);
        this.playSecondInning(cricketGame);
//        this.displayResult();
    }

    private void setupMatch(CricketMatchDto cricketGame , int overs){
             cricketGame.setMatchOvers(overs);
             scoreBoardService.setScoreBoard(cricketGame.getMatchOvers());
    }

    private void setTeamInfo(CricketMatchDto cricketGame , String team1Name , String team2Name) {
        cricketGame.setTeam1(new TeamDto(teamService.loadTeamDetails(team1Name)));
        cricketGame.setTeam2(new TeamDto(teamService.loadTeamDetails(team2Name)));
        teamService.setPlayerDetails(cricketGame);
    }

    private void coinToss(CricketMatchDto cricketGame){
        int tossResult = MatchCalculationsUtils.coinTossResult();
        if (tossResult == 1) {
            chooseBatOrField(cricketGame.getTeam1() , cricketGame.getTeam2());
        } else {
            chooseBatOrField(cricketGame.getTeam2() , cricketGame.getTeam1());
        }
    }

    private void chooseBatOrField(TeamDto tossWinner , TeamDto tossLoser){
        String autoTossChoice = MatchCalculationsUtils.tossChoice() == 1 ? "BAT" : "FIELD";
        TossChoices tossChoice = TossChoices.valueOf(autoTossChoice);

        if(tossChoice.choseBatting()){
            scoreBoardService.setPlayingTeams(tossWinner , tossLoser);
        }else{
            scoreBoardService.setPlayingTeams(tossLoser , tossWinner);
        }
    }

    private void playFirstInning(CricketMatchDto cricketGame){
        System.out.println("\n** Start of 1st inning **");
        this.playInning(cricketGame ,MatchConstants.FIRST_INNING , scoreBoardService.getTeamBattingFirst());
        scoreBoardService.updateTargetScore();
        scoreBoardService.getTeamBattingFirst().displayTeamScorecard();
    }

    private void inningsBreak(CricketMatchDto cricketGame){
        System.out.println("\n** Innings break **");
        scoreBoardService.displayScoreBoard(MatchConstants.FIRST_INNING);
        System.out.println("Target : " + scoreBoardService.getTargetScore());
        System.out.println(scoreBoardService.getTeamFieldingFirst().getTeamName() + " need " + scoreBoardService.getTargetScore() + " runs in " + cricketGame.getMatchOvers() * 6 + " balls.");
    }

    private void playSecondInning(CricketMatchDto cricketGame){
        System.out.println("\n** Start of 2nd inning **");
        this.playInning( cricketGame , MatchConstants.SECOND_INNING , scoreBoardService.getTeamFieldingFirst());
        scoreBoardService.getTeamFieldingFirst().displayTeamScorecard();
    }

    private void playInning(CricketMatchDto cricketGame ,int inning , TeamDto teamBatting){
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
