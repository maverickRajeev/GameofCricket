package com.tekion.service;

import com.tekion.dto.ScoreBoard;
import com.tekion.dto.Team;

public interface ScoreBoardService {
    void displayScoreBoard();
    void setPlayingTeams(ScoreBoard scoreBoard , Team teamBattingFirst , Team teamFieldingFirst);
    void updateScore(ScoreBoard scoreBoard , int ballScore , int inning);
}
