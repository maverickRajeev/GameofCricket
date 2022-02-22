package com.tekion.service;

import com.tekion.dto.Team;

public interface ScoreBoardService {
    void displayScoreBoard();
    void setPlayingTeams(Team teamBattingFirst , Team teamFieldingFirst);
    void updateScore(int ballScore , int inning);
}
