package com.tekion.cricketGame.playerService.dto;

import com.tekion.cricketGame.playerService.bean.PlayerBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PlayerMapper implements RowMapper<PlayerBean> {
    @Override
    public PlayerBean mapRow(ResultSet rs , int rowNum) throws SQLException {
        PlayerBean player = new PlayerBean();
        player.setPlayerId(rs.getInt(1));
        player.setTeamId(rs.getInt(2));
        player.setPlayerName(rs.getString(3));
        player.setCreatedTime(rs.getLong(4));
        player.setModifiedTime(rs.getLong(5));
        player.setIsDeleted(rs.getBoolean(6));
        return player;
    };
}


