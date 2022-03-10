package com.tekion.cricketGame.scoreBoardService;

import com.tekion.cricketGame.constants.MatchConstants;
import com.tekion.cricketGame.constants.RunConstants;
import com.tekion.cricketGame.scoreBoardService.dto.ScoreBoardDto;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import org.springframework.stereotype.Service;

@Service
public class ScoreBoardServiceImpl implements ScoreBoardService {

    @Override
    public void displayScoreBoard(ScoreBoardDto scoreBoard , int inning){
         if(inning == MatchConstants.FIRST_INNING){
             displayScoreFirstInning(scoreBoard);
         }else{
            displayScoreSecondInning(scoreBoard);
         }
    }

    @Override
    public void setPlayingTeams(ScoreBoardDto scoreBoard , TeamDto teamBattingFirst , TeamDto teamFieldingFirst){
        scoreBoard.setTeamBattingFirst(teamBattingFirst);
        scoreBoard.setTeamFieldingFirst(teamFieldingFirst);
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
    public void updateScore(ScoreBoardDto scoreBoard , int ballScore , int inning){
        if(inning == MatchConstants.FIRST_INNING)
            updateScoreFirstInning(scoreBoard , ballScore);
        else
            updateScoreSecondInning(scoreBoard , ballScore);
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
    public void updateTargetScore(ScoreBoardDto scoreBoard){
        scoreBoard.setTargetScore();
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

    private void updateScoreFirstInning(ScoreBoardDto scoreBoard , int ballScore){
        if(ballScore == RunConstants.WICKET){
            scoreBoard.fallWicketFirstInning();
        }else{
            scoreBoard.updateFirstInningScore(ballScore);
        }
    }

    private void updateScoreSecondInning(ScoreBoardDto scoreBoard , int ballScore){
        if(ballScore == RunConstants.WICKET){
            scoreBoard.fallWicketSecondInning();
        }else{
            scoreBoard.updateSecondInningScore(ballScore);
        }
    }


}
