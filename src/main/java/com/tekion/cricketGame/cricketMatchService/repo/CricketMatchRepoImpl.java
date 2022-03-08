package com.tekion.cricketGame.cricketMatchService.repo;

import com.tekion.cricketGame.cricketMatchService.bean.CricketMatchBean;
import com.tekion.cricketGame.cricketMatchService.dto.MatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CricketMatchRepoImpl implements CricketMatchRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createMatch(CricketMatchBean cricketMatchBean){
        String sqlStatement = "INSERT INTO match (seriesId , tossWinnerTeamId , winnerTeamId , winningMarginByRuns , winningMarginByWickets , matchTied ," +
                "createdTime , modifiedTime , isDeleted)" + "values (? , ? , ? , ? , ? , ? , ? , ? , ?)";
        jdbcTemplate.update(sqlStatement , cricketMatchBean.getSeriesId() , cricketMatchBean.getTossWinnerTeamId() , cricketMatchBean.getWinnerTeamId() , cricketMatchBean.getWinningMarginByRuns()
                , cricketMatchBean.getWinningMarginByWickets() , cricketMatchBean.getMatchTied() , System.currentTimeMillis() ,  System.currentTimeMillis() , false);
    }

    @Override
    public boolean checkMatchId(int matchId){
        String sqlStatement = "SELECT COUNT(*) FROM match WHERE matchId = ?";
        int count = jdbcTemplate.queryForObject(sqlStatement , new Object[]{matchId} , Integer.class);
        if(count == 1)
            return true;
        else
            return false;
    }

    @Override
    public CricketMatchBean getMatchDetailsById(int matchId){
        String sqlStatement = "SELECT * FROM match WHERE matchId = ? ";
        return jdbcTemplate.queryForObject(sqlStatement , new MatchMapper() , matchId);
    }

    @Override
    public List<CricketMatchBean> getAllMatchesBySeriesId(int seriesId){
        String sqlStatement = "SELECT * FROM match WHERE seriesId = ? ";
        List<CricketMatchBean> matches = jdbcTemplate.queryForList(sqlStatement , new MatchMapper() , seriesId);
    }

}
