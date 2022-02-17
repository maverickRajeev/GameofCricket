package com.tekion.service;

import com.tekion.constants.MatchConstants;
import com.tekion.constants.RunConstants;
import com.tekion.dto.ScoreBoard;
import com.tekion.dto.Team;

public class ScoreBoardServiceImpl implements ScoreBoardService {

    public void displayScoreBoard(){

    }

    public void setPlayingTeams(ScoreBoard scoreBoard , Team teamBattingFirst , Team teamFieldingFirst){
        scoreBoard.setTeamBattingFirst(teamBattingFirst);
        scoreBoard.setTeamFieldingFirst(teamFieldingFirst);
    }

    public void updateScore(ScoreBoard scoreBoard , int ballScore , int inning){
        if(inning == MatchConstants.FIRST_INNING)
            updateScoreFirstInning(scoreBoard , ballScore);
        else
            updateScoreSecondInning(scoreBoard , ballScore);
    }

    private void updateScoreFirstInning(ScoreBoard scoreBoard , int ballScore){
            if( ballScore == RunConstants.WICKET){
                 scoreBoard.fallWicketFirstInning();
            }else{
                 scoreBoard.updateFirstInningScore(ballScore);
            }
    }

    private void updateScoreSecondInning(ScoreBoard scoreBoard , int ballScore){
            if(ballScore == RunConstants.WICKET){
                scoreBoard.fallWicketSecondInning();
            }else{
                scoreBoard.updateSecondInningScore(ballScore);
            }
    }

}
