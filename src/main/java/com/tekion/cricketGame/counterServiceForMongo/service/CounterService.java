package com.tekion.cricketGame.counterServiceForMongo.service;

import com.tekion.cricketGame.counterServiceForMongo.beans.CounterBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class CounterService {

    private final MongoOperations mongo;

    @Autowired
    public CounterService(MongoOperations mongo){
        this.mongo = mongo;
    }

    public int getNextSequence(String collectionName) {
        CounterBean counter = mongo.findAndModify(query(where("_id").is(collectionName)),
                new Update().inc("seq", 1),
                options().returnNew(true),
                CounterBean.class);
        return counter.getSeq();
    }

}
