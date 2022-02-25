package com.tekion.service;

import com.tekion.dto.Team;

public interface ScoreBoardService {
    void setScoreBoard(int matchOvers);
    void displayScoreBoard(int inning);
    void setPlayingTeams(Team teamBattingFirst , Team teamFieldingFirst);
    Team getTeamBattingFirst();
    Team getTeamFieldingFirst();
    void updateScore(int ballScore , int inning);
    int getTeamScore(int inning);
    int getTeamWickets(int inning);
    void updateTargetScore();
    int getTargetScore();
}
