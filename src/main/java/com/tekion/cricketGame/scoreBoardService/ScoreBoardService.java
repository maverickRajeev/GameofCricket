package com.tekion.cricketGame.scoreBoardService;

import com.tekion.cricketGame.scoreBoardService.dto.ScoreBoardDto;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import org.springframework.stereotype.Service;

@Service
public interface ScoreBoardService {
    void displayScoreBoard(ScoreBoardDto scoreBoard , int inning);
    void setPlayingTeams(ScoreBoardDto scoreBoard , TeamDto teamBattingFirst , TeamDto teamFieldingFirst);
    TeamDto getTeamBattingFirst(ScoreBoardDto scoreBoard);
    TeamDto getTeamFieldingFirst(ScoreBoardDto scoreBoard);
    void updateScore(ScoreBoardDto scoreBoard , int ballScore , int inning);
    int getTeamScore(ScoreBoardDto scoreBoard , int inning);
    int getTeamWickets(ScoreBoardDto scoreBoard , int inning);
    void updateTargetScore(ScoreBoardDto scoreBoard);
    int getTargetScore(ScoreBoardDto scoreBoard);
}
