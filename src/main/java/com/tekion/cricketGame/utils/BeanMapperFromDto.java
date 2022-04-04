package com.tekion.cricketGame.utils;

import com.tekion.cricketGame.config.DatabaseConfig.DataProvider;
import com.tekion.cricketGame.cricketMatchService.bean.CricketMatchBean;
import com.tekion.cricketGame.cricketMatchService.dto.CricketMatchDto;
import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import com.tekion.cricketGame.cricketSeriesService.dto.CricketSeriesDto;
import com.tekion.cricketGame.playerService.bean.PlayerStatsBean;
import com.tekion.cricketGame.playerService.dto.PlayerDto;
import com.tekion.cricketGame.scoreBoardService.bean.MatchScoreBoardBean;
import com.tekion.cricketGame.scoreBoardService.dto.ScoreBoardDto;
import com.tekion.cricketGame.teamService.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanMapperFromDto {

    private final TeamRepository teamRepo;

    @Autowired
    public BeanMapperFromDto(DataProvider dataProvider){
        this.teamRepo = dataProvider.getRepoFile(TeamRepository.class);
    }

    public CricketSeriesBean mapSeriesDtoToBean(CricketSeriesDto cricketSeries){

        CricketSeriesBean newSeries = new CricketSeriesBean();
        newSeries.setSeriesType(cricketSeries.getNumberOfOvers());
        newSeries.setNumberOfMatches(cricketSeries.getNumberOfMatches());
        newSeries.setTeam1Id(teamRepo.getIdByTeamName(cricketSeries.getSeriesTeam1().getTeamName()));
        newSeries.setTeam2Id(teamRepo.getIdByTeamName(cricketSeries.getSeriesTeam2().getTeamName()));
        newSeries.setNumberOfMatchesWonByTeam1(cricketSeries.getNumberOfMatchesWonByTeam1());
        newSeries.setNumberOfMatchesWonByTeam2(cricketSeries.getNumberOfMatchesWonByTeam2());
        newSeries.setNumberOfMatchesTied(cricketSeries.getNumberOfMatchesTied());

        return newSeries;
    }

    public CricketMatchBean mapMatchDtoToBean(CricketMatchDto cricketMatch , ScoreBoardDto scoreBoard , int seriesId){

        CricketMatchBean newMatch = new CricketMatchBean();
        newMatch.setSeriesId(seriesId);
        newMatch.setTossWinnerTeamId(teamRepo.getIdByTeamName(cricketMatch.getTossWinnerTeam().getTeamName()));
        newMatch.setWinnerTeamId(teamRepo.getIdByTeamName(cricketMatch.getWinnerTeam().getTeamName()));

        if(scoreBoard.getFirstInningScore() > scoreBoard.getSecondInningScore()){
            newMatch.setMatchTied(false);
            newMatch.setWinningMarginByRuns(scoreBoard.getFirstInningScore() - scoreBoard.getSecondInningScore());
        }else if(scoreBoard.getSecondInningScore() > scoreBoard.getFirstInningScore()){
            newMatch.setMatchTied(false);
            newMatch.setWinningMarginByWickets(10 - scoreBoard.getSecondInningWickets());
        }else{
            newMatch.setMatchTied(true);
        }

        return newMatch;
    }

    public MatchScoreBoardBean mapScoreBoardDtoToBean(ScoreBoardDto scoreBoard , int matchId){
        MatchScoreBoardBean newScoreBoard = new MatchScoreBoardBean();
        newScoreBoard.setMatchId(matchId);
        newScoreBoard.setTeamBattingFirstId(teamRepo.getIdByTeamName(scoreBoard.getTeamBattingFirst().getTeamName()));
        newScoreBoard.setTeamFieldingFirstId(teamRepo.getIdByTeamName(scoreBoard.getTeamFieldingFirst().getTeamName()));
        newScoreBoard.setFirstInningScore(scoreBoard.getFirstInningScore());
        newScoreBoard.setFirstInningWickets(scoreBoard.getFirstInningWickets());
        newScoreBoard.setFirstInningBallsCompleted(scoreBoard.getFirstInningBallsCompleted());
        newScoreBoard.setSecondInningScore(scoreBoard.getSecondInningScore());
        newScoreBoard.setSecondInningWickets(scoreBoard.getSecondInningWickets());
        newScoreBoard.setSecondInningBallsCompleted(scoreBoard.getSecondInningBallsCompleted());
        return newScoreBoard;
    }

    public PlayerStatsBean mapPlayerStatsDtoToBean(PlayerDto player , int playerId,  int matchId){
        PlayerStatsBean playerStat = new PlayerStatsBean();
        playerStat.setPlayerId(playerId);
        playerStat.setMatchId(matchId);
        playerStat.setRunScored(player.getPlayerScore());
        playerStat.setBallsPlayed(player.getBallsPlayed());
        return playerStat;
    }
}
