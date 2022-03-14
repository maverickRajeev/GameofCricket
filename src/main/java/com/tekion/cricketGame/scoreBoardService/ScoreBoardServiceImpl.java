package com.tekion.cricketGame.scoreBoardService;

import com.tekion.cricketGame.constants.MatchConstants;
import com.tekion.cricketGame.constants.RunConstants;
import com.tekion.cricketGame.scoreBoardService.bean.MatchScoreBoardBean;
import com.tekion.cricketGame.scoreBoardService.dto.ScoreBoardDto;
import com.tekion.cricketGame.scoreBoardService.repo.ScoreBoardRepository;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import com.tekion.cricketGame.utils.BeanMapperFromDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoreBoardServiceImpl implements ScoreBoardService {

    @Autowired
    private ScoreBoardRepository scoreBoardRepository;

    @Autowired
    private BeanMapperFromDto beanMapperFromDto;

    @Override
    public void createScoreBoard(ScoreBoardDto scoreBoard, int matchId) {
        MatchScoreBoardBean scoreBoardBean = beanMapperFromDto.mapScoreBoardDtoToBean(scoreBoard , matchId);
        scoreBoardRepository.createScoreBoard(scoreBoardBean);
    }

    @Override
    public void displayScoreBoard(ScoreBoardDto scoreBoard , int inning){
         if(inning == MatchConstants.FIRST_INNING){
             displayScoreFirstInning(scoreBoard);
         }else{
            displayScoreSecondInning(scoreBoard);
         }
    }

    @Override
    public ScoreBoardDto setPlayingTeams(ScoreBoardDto scoreBoard , TeamDto teamBattingFirst , TeamDto teamFieldingFirst){
        scoreBoard.setTeamBattingFirst(teamBattingFirst);
        scoreBoard.setTeamFieldingFirst(teamFieldingFirst);
        return scoreBoard;
    }

    @Override
    public TeamDto getTeamBattingFirst(ScoreBoardDto scoreBoard) {
        return scoreBoard.getTeamBattingFirst();
    }

    @Override
    public TeamDto getTeamFieldingFirst(ScoreBoardDto scoreBoard){
        return scoreBoard.getTeamFieldingFirst();
    }

    @Override
    public ScoreBoardDto updateScore(ScoreBoardDto scoreBoard , int ballScore , int inning){
        if(inning == MatchConstants.FIRST_INNING)
            scoreBoard = updateScoreFirstInning(scoreBoard , ballScore);
        else
            scoreBoard = updateScoreSecondInning(scoreBoard , ballScore);
        return scoreBoard;
    }

    @Override
    public int getTeamScore(ScoreBoardDto scoreBoard , int inning){
        if(inning == MatchConstants.FIRST_INNING)
            return scoreBoard.getFirstInningScore();
        else
            return scoreBoard.getSecondInningScore();
    }

    @Override
    public int getTeamWickets(ScoreBoardDto scoreBoard , int inning){
        if(inning == MatchConstants.FIRST_INNING)
            return scoreBoard.getFirstInningWickets();
        else
            return scoreBoard.getSecondInningWickets();
    }

    @Override
    public ScoreBoardDto updateTargetScore(ScoreBoardDto scoreBoard){
        scoreBoard.setTargetScore();
        return scoreBoard;
    }

    @Override
    public int getTargetScore(ScoreBoardDto scoreBoard){
        return scoreBoard.getTargetScore();
    }

    @Override
    public TeamDto getHighestScoringTeam(ScoreBoardDto scoreBoard){
         if(scoreBoard.getFirstInningScore() > scoreBoard.getSecondInningScore())
             return scoreBoard.getTeamBattingFirst();
         else
             return scoreBoard.getTeamFieldingFirst();
    }

    @Override
    public MatchScoreBoardBean getScoreBoardDetails(int matchId){
        return scoreBoardRepository.fetchScoreBoardDetailsByMatchId(matchId);
    }

    private void displayScoreFirstInning(ScoreBoardDto scoreBoard){
        System.out.printf("\n%s: %d/%d (%d.%d Overs)%n",
                scoreBoard.getTeamBattingFirst().getTeamName(),
                scoreBoard.getFirstInningScore(),
                scoreBoard.getFirstInningWickets(),
                scoreBoard.getFirstInningBallsCompleted()/6,
                scoreBoard.getSecondInningBallsCompleted()%6);
    }

    private void displayScoreSecondInning(ScoreBoardDto scoreBoard){
        System.out.printf("\n%s: %d/%d (%d.%d Overs)%n",
                scoreBoard.getTeamFieldingFirst().getTeamName(),
                scoreBoard.getSecondInningScore(),
                scoreBoard.getSecondInningWickets(),
                scoreBoard.getSecondInningBallsCompleted()/6,
                scoreBoard.getSecondInningBallsCompleted()%6);
    }

    private ScoreBoardDto updateScoreFirstInning(ScoreBoardDto scoreBoard , int ballScore){
        if(ballScore == RunConstants.WICKET){
            scoreBoard.fallWicketFirstInning();
        }else{
            scoreBoard.updateFirstInningScore(ballScore);
        }
        return scoreBoard;
    }

    private ScoreBoardDto updateScoreSecondInning(ScoreBoardDto scoreBoard , int ballScore){
        if(ballScore == RunConstants.WICKET){
            scoreBoard.fallWicketSecondInning();
        }else{
            scoreBoard.updateSecondInningScore(ballScore);
        }
        return scoreBoard;
    }


}
