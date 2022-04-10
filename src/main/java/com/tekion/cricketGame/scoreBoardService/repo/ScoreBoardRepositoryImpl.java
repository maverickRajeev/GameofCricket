package com.tekion.cricketGame.scoreBoardService.repo;

import com.tekion.cricketGame.config.DatabaseConfig.ClassMapper;
import com.tekion.cricketGame.config.DatabaseConfig.ClassMapperMetaInfo;
import com.tekion.cricketGame.config.DatabaseConfig.SqlRepo;
import com.tekion.cricketGame.scoreBoardService.bean.MatchScoreBoardBean;
import com.tekion.cricketGame.scoreBoardService.dto.ScoreBoardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@ClassMapperMetaInfo(getClassName = ClassMapper.SCOREBOARD_REPO)
public class ScoreBoardRepositoryImpl implements ScoreBoardRepository , SqlRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createScoreBoard(MatchScoreBoardBean scoreBoardBean){
        String sqlStatement = "INSERT INTO match_scoreboard (matchId , teamBattingFirstId , teamFieldingFirstId , firstInningScore , firstInningWickets , " +
                "firstInningBallsCompleted , secondInningScore , secondInningWickets ,secondInningBallsCompleted ,  createdTime , modifiedTime , isDeleted)" + "values (? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
        jdbcTemplate.update(sqlStatement , scoreBoardBean.getMatchId() , scoreBoardBean.getTeamBattingFirstId() , scoreBoardBean.getTeamFieldingFirstId() , scoreBoardBean.getFirstInningScore() , scoreBoardBean.getFirstInningWickets(),
                scoreBoardBean.getFirstInningBallsCompleted() , scoreBoardBean.getSecondInningScore() , scoreBoardBean.getSecondInningWickets() , scoreBoardBean.getSecondInningBallsCompleted() , System.currentTimeMillis() ,  System.currentTimeMillis() , false);
    }

    @Override
    public MatchScoreBoardBean fetchScoreBoardDetailsByMatchId(int matchId){
        String sqlStatement = "SELECT * FROM match_scoreboard WHERE matchId = ? ";
        return jdbcTemplate.queryForObject(sqlStatement , new ScoreBoardMapper() , matchId);
    }

}
