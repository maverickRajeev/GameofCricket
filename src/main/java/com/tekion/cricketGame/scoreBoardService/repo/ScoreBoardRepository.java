package com.tekion.cricketGame.scoreBoardService.repo;

import com.tekion.cricketGame.scoreBoardService.bean.MatchScoreBoardBean;
import org.springframework.stereotype.Repository;

@Repository
public interface ScoreBoardRepository {
    void createScoreBoard(MatchScoreBoardBean scoreBoardBean);
    MatchScoreBoardBean fetchScoreBoardDetailsByMatchId(int matchId);
}
