package com.tekion.cricketGame.playerService.repo;

import com.tekion.cricketGame.counterServiceForMongo.service.CounterService;
import com.tekion.cricketGame.playerService.bean.PlayerBean;
import com.tekion.cricketGame.playerService.bean.PlayerStatsBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Primary
public class PlayerMongodbRepositoryImpl implements PlayerRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CounterService counterService;

    @Override
    public void createPlayer(int teamId, String playerName) {
        PlayerBean player = new PlayerBean();
        player.setPlayerId(counterService.getNextSequence("player"));
        player.setTeamId(teamId);
        player.setPlayerName(playerName);
        player.setCreatedTime(System.currentTimeMillis());
        player.setModifiedTime(System.currentTimeMillis());
        player.setIsDeleted(false);
        mongoTemplate.save(player , "player");
    }

    @Override
    public boolean ifCheckPlayerExists(int teamId, String playerName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("teamId").is(teamId).and("playerName").is(playerName));
        long count = mongoTemplate.count(query , "player");
        return count > 0;
    }

    @Override
    public boolean checkIfPlayerIdExists(int playerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("playerId").is(playerId));
        long count = mongoTemplate.count(query , "player");
        return count > 0;
    }

    @Override
    public int getPlayerIdByTeamIdAndPlayerName(int teamId, String playerName) {
        Query query = new Query();
        query.addCriteria(Criteria.where("teamId").is(teamId).and("playerName").is(playerName));
        PlayerBean player =  mongoTemplate.findOne(query , PlayerBean.class , "player");
        return player.getPlayerId();
    }

    @Override
    public PlayerBean getPlayerDetails(int playerId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("playerId").is(playerId));
        PlayerBean player = mongoTemplate.findOne(query , PlayerBean.class , "player");
        return player;
    }

    @Override
    public List<PlayerBean> getAllPlayersByTeamId(int teamId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("teamId").is(teamId));
        return mongoTemplate.find(query , PlayerBean.class , "player");
    }

    @Override
    public void addPlayerStat(PlayerStatsBean playerStatsBean) {
        PlayerStatsBean playerStats = new PlayerStatsBean();
        playerStats.setCreatedTime(System.currentTimeMillis());
        playerStats.setModifiedTime(System.currentTimeMillis());
        playerStats.setIsDeleted(false);
        mongoTemplate.save(playerStats , "player_stats");
    }

    @Override
    public PlayerStatsBean fetchPlayerStatsByMatchId(int playerId, int matchId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("playerId").is(playerId).and("matchId").is(matchId));
        return mongoTemplate.findOne(query , PlayerStatsBean.class , "player_stats");
    }
}
