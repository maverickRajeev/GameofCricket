package com.tekion.cricketGame.scoreBoardService;

import com.tekion.cricketGame.constants.MatchConstants;
import com.tekion.cricketGame.constants.RunConstants;
import com.tekion.cricketGame.scoreBoardService.dto.ScoreBoardDto;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import org.springframework.stereotype.Service;

@Service
public class ScoreBoardServiceImpl implements ScoreBoardService {

    public void setScoreBoard(ScoreBoardDto scoreBoard , int matchOvers){

    }

    public void displayScoreBoard(int inning){
         if(inning == MatchConstants.FIRST_INNING){
             displayScoreFirstInning();
         }else{
            displayScoreSecondInning();
         }
    }

    public void setPlayingTeams(TeamDto teamBattingFirst , TeamDto teamFieldingFirst){
        scoreBoard.setTeamBattingFirst(teamBattingFirst);
        scoreBoard.setTeamFieldingFirst(teamFieldingFirst);
    }

    public TeamDto getTeamBattingFirst() {
        return scoreBoard.getTeamBattingFirst();
    }

    public TeamDto getTeamFieldingFirst(){
        return scoreBoard.getTeamFieldingFirst();
    }

    public void updateScore(int ballScore , int inning){
        if(inning == MatchConstants.FIRST_INNING)
            updateScoreFirstInning(ballScore);
        else
            updateScoreSecondInning(ballScore);
    }

    public int getTeamScore(int inning){
        if(inning == MatchConstants.FIRST_INNING)
            return scoreBoard.getFirstInningScore();
        else
            return scoreBoard.getSecondInningScore();
    }

    public int getTeamWickets(int inning){
        if(inning == MatchConstants.FIRST_INNING)
            return scoreBoard.getFirstInningWickets();
        else
            return scoreBoard.getSecondInningWickets();
    }

    public void updateTargetScore(){
        scoreBoard.setTargetScore();
    }

    public int getTargetScore(){
        return scoreBoard.getTargetScore();
    }

    private void displayScoreFirstInning(){
        System.out.printf("\n%s: %d/%d (%d.%d Overs)%n",
                scoreBoard.getTeamBattingFirst().getTeamName(),
                scoreBoard.getFirstInningScore(),
                scoreBoard.getFirstInningWickets(),
                scoreBoard.getFirstInningBallsCompleted()/6,
                scoreBoard.getSecondInningBallsCompleted()%6);
    }

    private void displayScoreSecondInning(){
        System.out.printf("\n%s: %d/%d (%d.%d Overs)%n",
                scoreBoard.getTeamFieldingFirst().getTeamName(),
                scoreBoard.getSecondInningScore(),
                scoreBoard.getSecondInningWickets(),
                scoreBoard.getSecondInningBallsCompleted()/6,
                scoreBoard.getSecondInningBallsCompleted()%6);
    }

    private void updateScoreFirstInning(int ballScore){
        if(ballScore == RunConstants.WICKET){
            scoreBoard.fallWicketFirstInning();
        }else{
            scoreBoard.updateFirstInningScore(ballScore);
        }
    }

    private void updateScoreSecondInning(int ballScore){
        if(ballScore == RunConstants.WICKET){
            scoreBoard.fallWicketSecondInning();
        }else{
            scoreBoard.updateSecondInningScore(ballScore);
        }
    }


}
