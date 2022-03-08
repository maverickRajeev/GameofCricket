package com.tekion.cricketGame.cricketSeriesService.repo;

import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import com.tekion.cricketGame.cricketSeriesService.dto.SeriesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CricketSeriesRepoImpl implements CricketSeriesRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createSeries(CricketSeriesBean cricketSeriesBean){
        String sqlStatement = "INSERT INTO series (seriesType , numberOfMatches , team1Id , team2Id , " +
                "noOfMatchesWonByTeam1 , noOfMatchesWonByTeam2 , noOfMatchesTied , createdTime , modifiedTime , isDeleted)" + "values (? , ? , ? , ? , ? , ? , ? , ? , ? , ?)";
        jdbcTemplate.update(sqlStatement , cricketSeriesBean.getSeriesType() ,cricketSeriesBean.getNumberOfMatches() , cricketSeriesBean.getTeam1Id() , cricketSeriesBean.getTeam2Id() , cricketSeriesBean.getNumberOfMatchesWonByTeam1() ,
                cricketSeriesBean.getNumberOfMatchesWonByTeam2() , cricketSeriesBean.getNumberOfMatchesTied() , System.currentTimeMillis() ,  System.currentTimeMillis() , false);
    }

    @Override
    public boolean checkSeriesId(int seriesId){
        String sqlStatement = "SELECT COUNT(*) FROM series WHERE seriesId = ?";
        int count = jdbcTemplate.queryForObject(sqlStatement , new Object[]{seriesId} , Integer.class);
        if(count == 1)
            return true;
        else
            return false;
    }

    @Override
    public CricketSeriesBean getSeriesDetailsById(int seriesId){
        String sqlStatement = "SELECT * FROM series WHERE seriesId = ? ";
        return jdbcTemplate.queryForObject(sqlStatement , new SeriesMapper() , seriesId);
    }

}
