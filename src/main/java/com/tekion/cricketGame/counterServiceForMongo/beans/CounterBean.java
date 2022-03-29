package com.tekion.cricketGame.counterServiceForMongo.beans;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="counters")
public class CounterBean {
    @Id
    private String id;
    private int seq;
}
