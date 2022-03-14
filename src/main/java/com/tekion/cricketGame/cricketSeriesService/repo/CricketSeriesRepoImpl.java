package com.tekion.cricketGame.cricketSeriesService.repo;

import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import com.tekion.cricketGame.cricketSeriesService.dto.SeriesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;

@Repository
public class CricketSeriesRepoImpl implements CricketSeriesRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int createSeries(CricketSeriesBean cricketSeriesBean){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sqlStatement = "INSERT INTO series (seriesType , numberOfMatches , team1Id , team2Id , " +
                "noOfMatchesWonByTeam1 , noOfMatchesWonByTeam2 , noOfMatchesTied , createdTime , modifiedTime , isDeleted)" + "values (? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlStatement , Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1 , cricketSeriesBean.getSeriesType());
            ps.setInt(2 , cricketSeriesBean.getNumberOfMatches());
            ps.setInt(3 , cricketSeriesBean.getTeam1Id());
            ps.setInt(4 , cricketSeriesBean.getTeam2Id());
            ps.setInt(5 , cricketSeriesBean.getNumberOfMatchesWonByTeam1());
            ps.setInt(6 , cricketSeriesBean.getNumberOfMatchesWonByTeam2());
            ps.setInt(7 , cricketSeriesBean.getNumberOfMatchesTied());
            ps.setLong(8 , System.currentTimeMillis());
            ps.setLong(9 , System.currentTimeMillis());
            ps.setBoolean(10 , false);
            return ps;
        } , keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public void updateSeriesByMatch(CricketSeriesBean cricketSeriesBean , int seriesId){
        String sqlStatement = "UPDATE series SET noOfMatchesWonByTeam1 = ? , noOfMatchesWonByTeam2 = ? , noOfMatchesTied = ? ,  modifiedTime = ? WHERE seriesId = ?";
        jdbcTemplate.update(sqlStatement , cricketSeriesBean.getNumberOfMatchesWonByTeam1() , cricketSeriesBean.getNumberOfMatchesWonByTeam2() , cricketSeriesBean.getNumberOfMatchesTied() , System.currentTimeMillis() , seriesId);
    }

    @Override
    public boolean checkSeriesId(int seriesId){
        String sqlStatement = "SELECT COUNT(*) FROM series WHERE seriesId = ?";
        int count = jdbcTemplate.queryForObject(sqlStatement , new Object[]{seriesId} , Integer.class);
        return count == 1;
    }

    @Override
    public CricketSeriesBean getSeriesDetailsById(int seriesId){
        String sqlStatement = "SELECT * FROM series WHERE seriesId = ? ";
        return jdbcTemplate.queryForObject(sqlStatement , new SeriesMapper() , seriesId);
    }

}
