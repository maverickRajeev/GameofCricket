package com.tekion.cricketGame.cricketSeriesService.bean;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("series")
public class CricketSeriesBean {

     @Column("seriesId")
     private int seriesId;

     @Column("seriesType")
     private int seriesType;

     @Column("numberOfMatches")
     private int numberOfMatches;

     @Column("team1Id")
     private int team1Id;

     @Column("team2Id")
     private int team2Id;

     @Column("noOfMatchesWonByTeam1")
     private int numberOfMatchesWonByTeam1;

     @Column("noOfMatchesWonByTeam2")
     private int numberOfMatchesWonByTeam2;

     @Column("noOfMatchesTied")
     private int numberOfMatchesTied;

     @Column("createdTime")
     private Long createdTime;

     @Column("modifiedTime")
     private Long modifiedTime;

     @Column("isDeleted")
     private Boolean isDeleted;

}
