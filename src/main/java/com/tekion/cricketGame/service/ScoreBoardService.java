package com.tekion.cricketGame.service;

import com.tekion.cricketGame.dto.TeamDto;

public interface ScoreBoardService {
    void setScoreBoard(int matchOvers);
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
