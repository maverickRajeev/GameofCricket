package com.tekion.cricketGame.playerService.repo;

import com.tekion.cricketGame.playerService.bean.PlayerBean;
import com.tekion.cricketGame.playerService.bean.PlayerStatsBean;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlayerRepository {
    void createPlayer(int teamId , String playerName);
    boolean ifCheckPlayerExists(int teamId , String playerName);
    boolean checkIfPlayerIdExists(int playerId);
    int getPlayerIdByTeamIdAndPlayerName(int teamId , String playerName);
    PlayerBean getPlayerDetails(int playerId);
    List<PlayerBean> getAllPlayersByTeamId(int teamId);
    void addPlayerStat(PlayerStatsBean playerStatsBean);
    PlayerStatsBean fetchPlayerStatsByMatchId(int playerId, int matchId);
}
