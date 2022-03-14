package com.tekion.cricketGame.cricketMatchService.dto;

import com.tekion.cricketGame.cricketMatchService.bean.CricketMatchBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MatchMapper implements RowMapper<CricketMatchBean> {
    @Override
    public CricketMatchBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        CricketMatchBean match = new CricketMatchBean();
        match.setSeriesId(rs.getInt(1));
        match.setMatchId(rs.getInt(2));
        match.setTossWinnerTeamId(rs.getInt(3));
        match.setWinnerTeamId(rs.getInt(4));
        match.setWinningMarginByRuns(rs.getInt(5));
        match.setWinningMarginByWickets(rs.getInt(6));
        match.setMatchTied(rs.getBoolean(7));
        match.setCreatedTime(rs.getLong(8));
        match.setModifiedTime(rs.getLong(9));
        match.setIsDeleted(rs.getBoolean(10));
        return match;
    }
}

