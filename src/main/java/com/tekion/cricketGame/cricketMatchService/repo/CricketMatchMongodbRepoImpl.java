package com.tekion.cricketGame.cricketMatchService.repo;

import com.tekion.cricketGame.config.DatabaseConfig.ClassMapper;
import com.tekion.cricketGame.config.DatabaseConfig.ClassMapperMetaInfo;
import com.tekion.cricketGame.config.DatabaseConfig.MongoRepo;
import com.tekion.cricketGame.counterServiceForMongo.service.CounterService;
import com.tekion.cricketGame.cricketMatchService.bean.CricketMatchBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@ClassMapperMetaInfo(getClassName = ClassMapper.CRICKET_MATCH_REPO)
public class CricketMatchMongodbRepoImpl implements CricketMatchRepo , MongoRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CounterService counterService;

    @Override
    public int createMatch(CricketMatchBean cricketMatchBean) {
        cricketMatchBean.setMatchId(counterService.getNextSequence("match"));
        cricketMatchBean.setCreatedTime(System.currentTimeMillis());
        cricketMatchBean.setModifiedTime(System.currentTimeMillis());
        cricketMatchBean.setIsDeleted(false);
        mongoTemplate.save(cricketMatchBean , "match");
        return cricketMatchBean.getMatchId();
    }

    @Override
    public boolean checkMatchId(int matchId) {
       Query query = new Query();
       query.addCriteria(Criteria.where("matchId").is(matchId));
       long count = mongoTemplate.count(query , "match");
       return count > 0;
    }

    @Override
    public CricketMatchBean getMatchDetailsById(int matchId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("matchId").is(matchId));
        return mongoTemplate.findOne(query , CricketMatchBean.class , "match");
    }

    @Override
    public List<CricketMatchBean> getAllMatchesBySeriesId(int seriesId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("seriesId").is(seriesId));
        return mongoTemplate.find(query , CricketMatchBean.class , "match");
    }
}
