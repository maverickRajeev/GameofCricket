package com.tekion.cricketGame.playerService;

import com.tekion.cricketGame.playerService.bean.PlayerBean;
import com.tekion.cricketGame.playerService.bean.PlayerStatsBean;
import com.tekion.cricketGame.playerService.dto.PlayerDto;
import org.springframework.stereotype.Service;

@Service
public interface PlayerService {
    int fetchPlayerId(int teamId , String playerName);
    boolean checkIfPlayerIdExists(int playerId);
    PlayerBean getPlayerDetails(int playerId);
    void addPlayerStat(PlayerDto player , int playerId , int matchId);
    PlayerStatsBean getPlayerStats(int playerId , int matchId);
}
