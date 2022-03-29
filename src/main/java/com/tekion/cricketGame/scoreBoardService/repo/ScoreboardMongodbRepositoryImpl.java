package com.tekion.cricketGame.scoreBoardService.repo;

import com.tekion.cricketGame.counterServiceForMongo.service.CounterService;
import com.tekion.cricketGame.scoreBoardService.bean.MatchScoreBoardBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.*;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class ScoreboardMongodbRepositoryImpl implements ScoreBoardRepository{

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CounterService counterService;


    @Override
    public void createScoreBoard(MatchScoreBoardBean scoreBoardBean) {
        scoreBoardBean.setScoreBoardId(counterService.getNextSequence("match_scoreboard"));
        scoreBoardBean.setCreatedTime(System.currentTimeMillis());
        scoreBoardBean.setModifiedTime(System.currentTimeMillis());
        scoreBoardBean.setIsDeleted(false);
        mongoTemplate.save(scoreBoardBean);
    }

    @Override
    public MatchScoreBoardBean fetchScoreBoardDetailsByMatchId(int matchId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("matchId").is(matchId));
        return mongoTemplate.findOne(query , MatchScoreBoardBean.class , "match_scoreboard");
    }
}
