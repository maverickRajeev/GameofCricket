package com.tekion.cricketGame.playerService.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlayerRepositoryImpl implements PlayerRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createPlayer(int teamId, String playerName) {
        String sqlStatement = "INSERT INTO player(teamId , playerName , createdTime , modifiedTime , isDeleted) values(?, ? , ? , ? , ?)";
        jdbcTemplate.update(sqlStatement , teamId , playerName , System.currentTimeMillis() , System.currentTimeMillis() , false);
    }

    @Override
    public Boolean ifCheckPlayerExists(int teamId , String playerName) {
        String sqlStatement = "SELECT EXISTS(SELECT * FROM player WHERE teamId = ? AND playerName = ?)";
        return jdbcTemplate.queryForObject(sqlStatement , Boolean.class , teamId , playerName);
    }

    @Override
    public int getIdByTeamIdAndPlayerName(int teamId, String playerName) {
        String sqlStatement = "SELECT playerId from player WHERE teamId = ? AND playerName = ?";
        return jdbcTemplate.queryForObject(sqlStatement , Integer.class , teamId , playerName);
    }
}
