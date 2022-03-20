package com.tekion.cricketGame.playerService;

import com.tekion.cricketGame.playerService.bean.PlayerBean;
import com.tekion.cricketGame.playerService.bean.PlayerStatsBean;
import com.tekion.cricketGame.playerService.dto.PlayerDto;
import com.tekion.cricketGame.playerService.repo.PlayerRepository;
import com.tekion.cricketGame.utils.BeanMapperFromDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class PlayerServiceImpl implements PlayerService{

    @Autowired
    private PlayerRepository playerRepo;

    @Autowired
    private BeanMapperFromDto beanMapperFromDto;

    @Override
    public int fetchPlayerId(int teamId, String playerName) {
        return playerRepo.getPlayerIdByTeamIdAndPlayerName(teamId , playerName);
    }

    @Override
    public boolean checkIfPlayerIdExists(int playerId){
        return playerRepo.checkIfPlayerIdExists(playerId);
    }

    @Override
    @Cacheable(cacheNames = "playerCache" , key = "#playerId")
    public PlayerBean getPlayerDetails(int playerId) {
        return playerRepo.getPlayerDetails(playerId);
    }

    @Override
    @CachePut(cacheNames = "playerStatCache" , key = "#playerId + #matchId")
    public void addPlayerStat(PlayerDto player , int playerId , int matchId){
        PlayerStatsBean playerStatsBean = beanMapperFromDto.mapPlayerStatsDtoToBean(player , playerId , matchId);
        playerRepo.addPlayerStat(playerStatsBean);
    }

    @Override
    @Cacheable(cacheNames = "playerStatCache" , key = "#playerId + #matchId")
    public PlayerStatsBean getPlayerStats(int playerId , int matchId){
        return playerRepo.fetchPlayerStatsByMatchId(playerId , matchId);
    }
}
