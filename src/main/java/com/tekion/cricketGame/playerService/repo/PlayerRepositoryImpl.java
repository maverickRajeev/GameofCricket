package com.tekion.cricketGame.playerService.repo;

import com.tekion.cricketGame.playerService.bean.PlayerBean;
import com.tekion.cricketGame.playerService.bean.PlayerStatsBean;
import com.tekion.cricketGame.playerService.dto.PlayerMapper;
import com.tekion.cricketGame.playerService.dto.PlayerStatsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public boolean ifCheckPlayerExists(int teamId , String playerName) {
        String sqlStatement = "SELECT EXISTS(SELECT * FROM player WHERE teamId = ? AND playerName = ?)";
        return jdbcTemplate.queryForObject(sqlStatement , Boolean.class , teamId , playerName);
    }

    @Override
    public boolean checkIfPlayerIdExists(int playerId) {
        String sqlStatement = "SELECT EXISTS(SELECT * FROM player WHERE playerId = ? )";
        return jdbcTemplate.queryForObject(sqlStatement , Boolean.class , playerId);
    }

    @Override
    public int getPlayerIdByTeamIdAndPlayerName(int teamId, String playerName) {
        String sqlStatement = "SELECT playerId from player WHERE teamId = ? AND playerName = ?";
        return jdbcTemplate.queryForObject(sqlStatement , Integer.class , teamId , playerName);
    }

    @Override
    public PlayerBean getPlayerDetails(int playerId){
        String sqlStatement = "SELECT * FROM player WHERE playerId = ? ";
        return jdbcTemplate.queryForObject(sqlStatement , new PlayerMapper() , playerId);
    }

    @Override
    public List<PlayerBean> getAllPlayersByTeamId(int teamId){
        String sqlStatement = "SELECT * from player WHERE teamId = ?";
        return jdbcTemplate.query(sqlStatement , new PlayerMapper() , teamId);
    }

    @Override
    public void addPlayerStat(PlayerStatsBean playerStatsBean){
        String sqlStatement = "INSERT INTO player_stats(playerId , matchId , runScored , ballsPlayed , createdTime , modifiedTime , isDeleted) " +
                "values(? , ? , ? , ? , ? , ? , ?)";
        jdbcTemplate.update(sqlStatement , playerStatsBean.getPlayerId() , playerStatsBean.getMatchId() , playerStatsBean.getRunScored() , playerStatsBean.getBallsPlayed()
         , System.currentTimeMillis() , System.currentTimeMillis() , false);
    }

    @Override
    public PlayerStatsBean fetchPlayerStatsByMatchId(int playerId , int matchId){
        String sqlStatement = "SELECT * FROM player_stats WHERE playerId = ? AND matchId = ?";
        return jdbcTemplate.queryForObject(sqlStatement , new PlayerStatsMapper() , playerId , matchId);
    }
}
