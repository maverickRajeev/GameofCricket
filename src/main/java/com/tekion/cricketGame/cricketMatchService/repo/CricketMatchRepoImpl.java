package com.tekion.cricketGame.cricketMatchService.repo;

import com.tekion.cricketGame.config.DatabaseConfig.ClassMapper;
import com.tekion.cricketGame.config.DatabaseConfig.ClassMapperMetaInfo;
import com.tekion.cricketGame.config.DatabaseConfig.SqlRepo;
import com.tekion.cricketGame.cricketMatchService.bean.CricketMatchBean;
import com.tekion.cricketGame.cricketMatchService.dto.MatchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
@ClassMapperMetaInfo(getClassName = ClassMapper.CRICKET_MATCH_REPO)
public class CricketMatchRepoImpl implements CricketMatchRepo , SqlRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int createMatch(CricketMatchBean cricketMatchBean){
        KeyHolder keyHolder = new GeneratedKeyHolder();

        String sqlStatement = "INSERT INTO cricket_stats.match (seriesId , tossWinnerTeamId , winnerTeamId , winningMarginByRuns , winningMarginByWickets , matchTied , " +
                "createdTime , modifiedTime , isDeleted)" + "values (? , ? , ? , ? , ? , ? , ? , ? , ?)";
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sqlStatement , Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1 , cricketMatchBean.getSeriesId());
            ps.setInt(2 , cricketMatchBean.getTossWinnerTeamId());
            ps.setInt(3 , cricketMatchBean.getWinnerTeamId());
            ps.setInt(4 , cricketMatchBean.getWinningMarginByRuns());
            ps.setInt(5 , cricketMatchBean.getWinningMarginByWickets());
            ps.setBoolean(6 , cricketMatchBean.getMatchTied());
            ps.setLong(7 , System.currentTimeMillis());
            ps.setLong(8 , System.currentTimeMillis());
            ps.setBoolean(9 , false);
            return ps;
        } , keyHolder);

        return keyHolder.getKey().intValue();
    }

    @Override
    public boolean checkMatchId(int matchId){
        String sqlStatement = "SELECT COUNT(*) FROM cricket_stats.match WHERE matchId = ?";
        int count = jdbcTemplate.queryForObject(sqlStatement , new Object[]{matchId} , Integer.class);
        return count == 1;
    }

    @Override
    public CricketMatchBean getMatchDetailsById(int matchId){
        String sqlStatement = "SELECT * FROM cricket_stats.match WHERE matchId = ? ";
        return jdbcTemplate.queryForObject(sqlStatement , new MatchMapper() , matchId);
    }

    @Override
    public List<CricketMatchBean> getAllMatchesBySeriesId(int seriesId){
        String sqlStatement = "SELECT * FROM cricket_stats.match WHERE seriesId = ? ";
        return jdbcTemplate.query(sqlStatement , new MatchMapper() , seriesId);
    }

}
