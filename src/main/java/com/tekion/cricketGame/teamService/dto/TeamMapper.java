package com.tekion.cricketGame.teamService.dto;

import com.tekion.cricketGame.teamService.bean.TeamBean;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TeamMapper implements RowMapper<TeamBean> {

    @Override
    public TeamBean mapRow(ResultSet rs, int rowNum) throws SQLException {

        TeamBean team = new TeamBean();
        team.setTeamId(rs.getInt(1));
        team.setTeamName(rs.getString(2));
        team.setCreatedTime(rs.getLong(3));
        team.setModifiedTime(rs.getLong(4));
        team.setIsDeleted(rs.getBoolean(5));
        return team;
    }
}
