package com.tekion.cricketGame.playerService.dto;

import com.tekion.cricketGame.playerService.bean.PlayerStatsBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerStatsMapper implements RowMapper<PlayerStatsBean> {

    @Override
    public PlayerStatsBean mapRow(ResultSet rs, int rowNum) throws SQLException {

       PlayerStatsBean playerStat = new PlayerStatsBean();
       playerStat.setPlayerId(rs.getInt(1));
       playerStat.setMatchId(rs.getInt(2));
       playerStat.setRunScored(rs.getInt(3));
       playerStat.setBallsPlayed(rs.getInt(4));
       playerStat.setCreatedTime(rs.getLong(5));
       playerStat.setModifiedTime(rs.getLong(6));
       playerStat.setIsDeleted(rs.getBoolean(7));

       return playerStat;
    }
}
