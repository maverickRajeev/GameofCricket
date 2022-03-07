package com.tekion.cricketGame.scoreBoardService;

import com.tekion.cricketGame.scoreBoardService.dto.ScoreBoardDto;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import org.springframework.stereotype.Service;

@Service
public interface ScoreBoardService {
    void setScoreBoard(ScoreBoardDto scoreBoard , int matchOvers);
    void displayScoreBoard(int inning);
    void setPlayingTeams(TeamDto teamBattingFirst , TeamDto teamFieldingFirst);
    TeamDto getTeamBattingFirst();
    TeamDto getTeamFieldingFirst();
    void updateScore(int ballScore , int inning);
    int getTeamScore(int inning);
    int getTeamWickets(int inning);
    void updateTargetScore();
    int getTargetScore();
}
