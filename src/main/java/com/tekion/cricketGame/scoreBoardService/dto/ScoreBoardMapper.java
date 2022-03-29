package com.tekion.cricketGame.scoreBoardService.dto;

import com.tekion.cricketGame.scoreBoardService.bean.MatchScoreBoardBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScoreBoardMapper implements RowMapper<MatchScoreBoardBean> {
    @Override
    public MatchScoreBoardBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        MatchScoreBoardBean scoreBoard = new MatchScoreBoardBean();
        scoreBoard.setScoreBoardId(rs.getInt(1));
        scoreBoard.setMatchId(rs.getInt(2));
        scoreBoard.setTeamBattingFirstId(rs.getInt(3));
        scoreBoard.setTeamFieldingFirstId(rs.getInt(4));
        scoreBoard.setFirstInningScore(rs.getInt(5));
        scoreBoard.setFirstInningWickets(rs.getInt(6));
        scoreBoard.setFirstInningBallsCompleted(rs.getInt(7));
        scoreBoard.setSecondInningScore(rs.getInt(8));
        scoreBoard.setSecondInningWickets(rs.getInt(9));
        scoreBoard.setSecondInningBallsCompleted(rs.getInt(10));
        scoreBoard.setCreatedTime(rs.getLong(11));
        scoreBoard.setModifiedTime(rs.getLong(12));
        scoreBoard.setIsDeleted(rs.getBoolean(13));
        return scoreBoard;
    }
}
