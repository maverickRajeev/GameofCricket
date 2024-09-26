package com.tekion.cricketGame.cricketSeriesService.dto;

import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SeriesMapper implements RowMapper<CricketSeriesBean> {
    @Override
    public CricketSeriesBean mapRow(ResultSet rs, int rowNum) throws SQLException {
        CricketSeriesBean cricketSeries = new CricketSeriesBean();
        cricketSeries.setSeriesId(rs.getInt(1));
        cricketSeries.setSeriesType(rs.getInt(2));
        cricketSeries.setNumberOfMatches(rs.getInt(3));
        cricketSeries.setTeam1Id(rs.getInt(4));
        cricketSeries.setTeam2Id(rs.getInt(5));
        cricketSeries.setNumberOfMatchesWonByTeam1(rs.getInt(6));
        cricketSeries.setNumberOfMatchesWonByTeam2(rs.getInt(7));
        cricketSeries.setNumberOfMatchesTied(rs.getInt(8));
        cricketSeries.setCreatedTime(rs.getLong(9));
        cricketSeries.setModifiedTime(rs.getLong(10));
        cricketSeries.setDeleted(rs.getBoolean(11));
        return cricketSeries;
    }
}
