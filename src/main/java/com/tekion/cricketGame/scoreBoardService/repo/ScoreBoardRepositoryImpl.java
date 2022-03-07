package com.tekion.cricketGame.scoreBoardService.repo;

import com.tekion.cricketGame.scoreBoardService.bean.MatchScoreBoardBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ScoreBoardRepositoryImpl implements ScoreBoardRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createScoreBoard(MatchScoreBoardBean scoreBoardBean){
        String sqlStatement = "INSERT INTO match_scoreboard (matchId , teamBattingFirstId , teamFieldingFirstId , firstInningScore , firstInningWickets , " +
                "firstInningBallsCompleted , secondInningScore , secondInningWickets ,secondInningBallsCompleted ,  createdTime , modifiedTime , isDeleted)" + "values (? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
        jdbcTemplate.update(sqlStatement , scoreBoardBean.getMatchId() , scoreBoardBean.getTeamBattingFirstId() , scoreBoardBean.getTeamFieldingFirstId() , scoreBoardBean.getFirstInningScore() , scoreBoardBean.getFirstInningWickets(),
                scoreBoardBean.getFirstInningBallsCompleted() , scoreBoardBean.getSecondInningScore() , scoreBoardBean.getSecondInningWickets() , scoreBoardBean.getSecondInningBallsCompleted() , System.currentTimeMillis() ,  System.currentTimeMillis() , false);
    }

}
