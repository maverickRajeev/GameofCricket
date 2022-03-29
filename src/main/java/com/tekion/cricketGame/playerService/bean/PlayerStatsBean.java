package com.tekion.cricketGame.playerService.bean;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("player_stats")
@Document(collection = "player_stats")
public class PlayerStatsBean {

    @Column("playerId")
    private int playerId;

    @Column("matchId")
    private int matchId;

    @Column("runScored")
    private int runScored;

    @Column("ballsPlayed")
    private int ballsPlayed;

    @Column("createdTime")
    private Long createdTime;

    @Column("modifiedTime")
    private Long modifiedTime;

    @Column("isDeleted")
    private Boolean isDeleted;

}
