package com.tekion.cricketGame.cricketMatchService;

import com.tekion.cricketGame.constants.MatchConstants;
import com.tekion.cricketGame.constants.RunConstants;
import com.tekion.cricketGame.cricketMatchService.bean.CricketMatchBean;
import com.tekion.cricketGame.cricketMatchService.dto.CricketMatchDto;
import com.tekion.cricketGame.cricketMatchService.repo.CricketMatchRepo;
import com.tekion.cricketGame.cricketMatchService.utils.MatchCalculationsUtils;
import com.tekion.cricketGame.cricketSeriesService.dto.CricketSeriesDto;
import com.tekion.cricketGame.enums.PlayerStatus;
import com.tekion.cricketGame.enums.TossChoices;
import com.tekion.cricketGame.playerService.PlayerService;
import com.tekion.cricketGame.playerService.dto.PlayerDto;
import com.tekion.cricketGame.scoreBoardService.ScoreBoardService;
import com.tekion.cricketGame.scoreBoardService.dto.ScoreBoardDto;
import com.tekion.cricketGame.teamService.TeamService;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import com.tekion.cricketGame.teamService.repo.TeamRepository;
import com.tekion.cricketGame.utils.BeanMapperFromDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CricketMatchServiceImpl implements CricketMatchService {

    private final TeamService teamService;
    private final ScoreBoardService scoreBoardService;
    private final PlayerService playerService;
    private final CricketMatchRepo cricketMatchRepo;
    private final TeamRepository teamRepo;
    private final BeanMapperFromDto beanMapperFromDto;

    @Autowired
    public CricketMatchServiceImpl(TeamService teamService , TeamRepository teamRepo , ScoreBoardService scoreBoardService , PlayerService playerService , CricketMatchRepo cricketMatchRepo , BeanMapperFromDto beanMapperFromDto){
        this.teamService = teamService;
        this.teamRepo = teamRepo;
        this.scoreBoardService = scoreBoardService;
        this.playerService = playerService;
        this.cricketMatchRepo = cricketMatchRepo;
        this.beanMapperFromDto = beanMapperFromDto;
    }

    @Override
    public TeamDto startCricketMatch(CricketSeriesDto cricketSeries , int seriesId){
        CricketMatchDto cricketGame = new CricketMatchDto();
        ScoreBoardDto scoreBoard = new ScoreBoardDto(cricketSeries.getNumberOfOvers());
        cricketGame.setScoreBoard(scoreBoard);
        cricketGame = this.setupMatch(cricketGame , cricketSeries.getNumberOfOvers());
        cricketGame = this.setTeamInfo(cricketGame , cricketSeries.getSeriesTeam1() , cricketSeries.getSeriesTeam2());
        cricketGame = this.playMatch(cricketGame , seriesId);
        return cricketGame.getWinnerTeam();
    }

    @Override
    public boolean checkIfMatchExists(int matchId){
        return cricketMatchRepo.checkMatchId(matchId);
    }

    @Override
    @Cacheable(cacheNames = "matchCache" , key = "#matchId")
    public CricketMatchBean getMatchDetails(int matchId) {
        return cricketMatchRepo.getMatchDetailsById(matchId);
    }

    @Override
    @Cacheable(cacheNames = "matchListCache" , key = "#seriesId")
    public List<CricketMatchBean> listAllMatchesBySeriesId(int seriesId){
        return cricketMatchRepo.getAllMatchesBySeriesId(seriesId);
    }

    private CricketMatchDto playMatch(CricketMatchDto cricketGame , int seriesId){
        cricketGame = this.coinToss(cricketGame);
        cricketGame = this.playFirstInning(cricketGame);
        this.inningsBreak(cricketGame);
        cricketGame = this.playSecondInning(cricketGame);
        cricketGame = this.getMatchResult(cricketGame);
        int newCreatedMatchId = this.updateMatchDb(cricketGame , seriesId);
        this.updateScoreBoardDb(cricketGame.getScoreBoard() , newCreatedMatchId);
        this.updatePlayerStats(cricketGame.getTeam1() , newCreatedMatchId);
        this.updatePlayerStats(cricketGame.getTeam2() , newCreatedMatchId);
        return cricketGame;
    }

    private CricketMatchDto setupMatch(CricketMatchDto cricketGame , int overs){
             cricketGame.setMatchOvers(overs);
             return cricketGame;
    }

    private CricketMatchDto setTeamInfo(CricketMatchDto cricketGame , TeamDto team1 , TeamDto team2) {
        cricketGame.setTeam1(team1);
        cricketGame.setTeam2(team2);
        cricketGame = teamService.setPlayerDetails(cricketGame);
        return cricketGame;
    }

    private CricketMatchDto coinToss(CricketMatchDto cricketGame){
        int tossResult = MatchCalculationsUtils.coinTossResult();
        if (tossResult == 1) {
            cricketGame.setTossWinnerTeam(cricketGame.getTeam1());
            cricketGame = chooseBatOrField(cricketGame , cricketGame.getTeam1() , cricketGame.getTeam2());
        } else {
            cricketGame.setTossWinnerTeam(cricketGame.getTeam2());
            cricketGame = chooseBatOrField(cricketGame , cricketGame.getTeam2() , cricketGame.getTeam1());
        }
        return cricketGame;
    }

    private CricketMatchDto chooseBatOrField(CricketMatchDto cricketGame , TeamDto tossWinner , TeamDto tossLoser){
        String autoTossChoice = MatchCalculationsUtils.tossChoice() == 1 ? "BAT" : "FIELD";
        TossChoices tossChoice = TossChoices.valueOf(autoTossChoice);

        if(tossChoice.choseBatting()){
            cricketGame.setScoreBoard(scoreBoardService.setPlayingTeams(cricketGame.getScoreBoard() , tossWinner , tossLoser));
        }else{
            cricketGame.setScoreBoard(scoreBoardService.setPlayingTeams(cricketGame.getScoreBoard() , tossLoser , tossWinner));
        }

        return cricketGame;
    }

    private CricketMatchDto playFirstInning(CricketMatchDto cricketGame){
        System.out.println("\n** Start of 1st inning **");
        cricketGame = this.playInning(cricketGame , MatchConstants.FIRST_INNING);
        cricketGame.setScoreBoard(scoreBoardService.updateTargetScore(cricketGame.getScoreBoard()));
        scoreBoardService.getTeamBattingFirst(cricketGame.getScoreBoard()).displayTeamScorecard();
        return cricketGame;
    }

    private void inningsBreak(CricketMatchDto cricketGame){
        System.out.println("\n** Innings break **");
        scoreBoardService.displayScoreBoard(cricketGame.getScoreBoard() , MatchConstants.FIRST_INNING);
        System.out.println("Target : " + scoreBoardService.getTargetScore(cricketGame.getScoreBoard()) );
        System.out.println(scoreBoardService.getTeamFieldingFirst(cricketGame.getScoreBoard()).getTeamName() + " need " + scoreBoardService.getTargetScore(cricketGame.getScoreBoard()) + " runs in " + cricketGame.getMatchOvers() * 6 + " balls.");
    }

    private CricketMatchDto playSecondInning(CricketMatchDto cricketGame){
        System.out.println("\n** Start of 2nd inning **");
        cricketGame = this.playInning( cricketGame ,  MatchConstants.SECOND_INNING );
        scoreBoardService.getTeamFieldingFirst(cricketGame.getScoreBoard()).displayTeamScorecard();
        return cricketGame;
    }

    private CricketMatchDto playInning(CricketMatchDto cricketGame , int inning ){
        for(int i = 0 ; i < cricketGame.getMatchOvers() ; i++){
            System.out.println("\nOver : " + (i+1));
            cricketGame = playOver(cricketGame , inning , cricketGame.getMatchOvers());
            scoreBoardService.displayScoreBoard(cricketGame.getScoreBoard() , inning);
            if(inning == MatchConstants.SECOND_INNING && scoreBoardService.getTeamScore(cricketGame.getScoreBoard() , MatchConstants.SECOND_INNING) >= scoreBoardService.getTargetScore(cricketGame.getScoreBoard()) )
                break;
            if(scoreBoardService.getTeamWickets(cricketGame.getScoreBoard() , inning) == 10)
                break;
        }
        return cricketGame;
    }

    private CricketMatchDto playOver(CricketMatchDto cricketGame , int inning , int matchOvers){
        int ballScore;
        TeamDto teamBatting;

        if(inning == MatchConstants.FIRST_INNING)
            teamBatting = scoreBoardService.getTeamBattingFirst(cricketGame.getScoreBoard());
        else
            teamBatting = scoreBoardService.getTeamFieldingFirst(cricketGame.getScoreBoard());

        for(int ball = 0 ; ball < 6 ; ball++){
            ballScore = playBall(matchOvers , teamBatting.getCurrentBatsMan());

            cricketGame.setScoreBoard(scoreBoardService.updateScore(cricketGame.getScoreBoard(), ballScore , inning));

            cricketGame = PlayerStatUpdateAndStrikeChange(cricketGame, inning , ballScore);

            if(inning == MatchConstants.SECOND_INNING && scoreBoardService.getTeamScore(cricketGame.getScoreBoard() , MatchConstants.SECOND_INNING) >= scoreBoardService.getTargetScore(cricketGame.getScoreBoard()) )
                break;
            if(scoreBoardService.getTeamWickets(cricketGame.getScoreBoard() , inning) == 10)
                break;
        }

        if(inning == MatchConstants.FIRST_INNING)
            teamBatting.changeTeamStrike();
        else
            teamBatting.changeTeamStrike();

        if(inning == MatchConstants.FIRST_INNING)
            cricketGame.getScoreBoard().setTeamBattingFirst(teamBatting);
        else
            cricketGame.getScoreBoard().setTeamFieldingFirst(teamBatting);

        return cricketGame;
    }

    private int playBall(int matchOvers , PlayerDto currentBatsman) {
        int runScored = MatchCalculationsUtils.eachBallScore(matchOvers, currentBatsman);
        return runScored;
    }

    private CricketMatchDto PlayerStatUpdateAndStrikeChange(CricketMatchDto cricketGame, int inning , int runScored){
        TeamDto teamBatting;

        if(inning == MatchConstants.FIRST_INNING)
            teamBatting = scoreBoardService.getTeamBattingFirst(cricketGame.getScoreBoard());
        else
            teamBatting = scoreBoardService.getTeamFieldingFirst(cricketGame.getScoreBoard());

        if( runScored == RunConstants.WICKET){
            teamBatting.getCurrentBatsMan().increaseBallsPlayed();
            teamBatting.getCurrentBatsMan().updatePlayerStatus(PlayerStatus.OUT);
            if(scoreBoardService.getTeamWickets(cricketGame.getScoreBoard() , inning) < 10)
               teamBatting.newPlayerChange();
        }else{
            teamBatting.getCurrentBatsMan().updatePlayerStatus(PlayerStatus.NOT_OUT);
            teamBatting.getCurrentBatsMan().increaseScore(runScored);
            teamBatting.getCurrentBatsMan().increaseBallsPlayed();
            if(runScored % 2 == 1){
                teamBatting.changeTeamStrike();
            }
        }

        if(inning == MatchConstants.FIRST_INNING)
            cricketGame.getScoreBoard().setTeamBattingFirst(teamBatting);
        else
            cricketGame.getScoreBoard().setTeamFieldingFirst(teamBatting);

        return cricketGame;
    }

    private int updateMatchDb(CricketMatchDto cricketMatch , int seriesId){
        CricketMatchBean matchBean = beanMapperFromDto.mapMatchDtoToBean(cricketMatch , cricketMatch.getScoreBoard() , seriesId);
        return cricketMatchRepo.createMatch(matchBean);
    }

    private void updateScoreBoardDb(ScoreBoardDto scoreBoard , int matchId){
        scoreBoardService.createScoreBoard(scoreBoard , matchId);
    }

    private void updatePlayerStats(TeamDto team , int matchId){

        for(int playerNum = 0 ; playerNum < MatchConstants.TEAM_SIZE ; playerNum++){
            int playerId = playerService.fetchPlayerId(teamRepo.getIdByTeamName(team.getTeamName()) , team.getPlayerById(playerNum).getPlayerName());
            playerService.addPlayerStat(team.getPlayerById(playerNum) , playerId , matchId);
        }

    }

    private CricketMatchDto getMatchResult(CricketMatchDto cricketMatch){
         cricketMatch.setWinnerTeam(scoreBoardService.getHighestScoringTeam(cricketMatch.getScoreBoard()));
         return cricketMatch;
    }

}
