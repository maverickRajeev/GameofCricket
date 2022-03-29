package com.tekion.cricketGame.cricketSeriesService.repo;

import com.tekion.cricketGame.counterServiceForMongo.service.CounterService;
import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class CricketSeriesMongodbRepoImpl implements CricketSeriesRepo {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private CounterService counterService;

    @Override
    public int createSeries(CricketSeriesBean cricketSeriesBean) {
        cricketSeriesBean.setSeriesId(counterService.getNextSequence("series"));
        cricketSeriesBean.setCreatedTime(System.currentTimeMillis());
        cricketSeriesBean.setModifiedTime(System.currentTimeMillis());
        cricketSeriesBean.setDeleted(false);
        mongoTemplate.save(cricketSeriesBean , "series");
        return cricketSeriesBean.getSeriesId();
    }

    @Override
    public void updateSeriesByMatch(CricketSeriesBean cricketSeriesBean, int seriesId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("seriesId").is(seriesId));
        Update update = new Update();
        update.set("numberOfMatchesWonByTeam1" , cricketSeriesBean.getNumberOfMatchesWonByTeam1());
        update.set("numberOfMatchesWonByTeam2" , cricketSeriesBean.getNumberOfMatchesWonByTeam2());
        update.set("numberOfMatchesTied" , cricketSeriesBean.getNumberOfMatchesTied());
        update.set("modifiedTime" , System.currentTimeMillis());
        mongoTemplate.updateFirst(query , update , CricketSeriesBean.class);
    }

    @Override
    public boolean checkSeriesId(int seriesId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("seriesId").is(seriesId));
        long count = mongoTemplate.count(query , "series");
        return count > 0;
    }

    @Override
    public CricketSeriesBean getSeriesDetailsById(int seriesId) {
        Query query = new Query();
        query.addCriteria(Criteria.where("seriesId").is(seriesId));
        return mongoTemplate.findOne(query , CricketSeriesBean.class , "series");
    }

}
