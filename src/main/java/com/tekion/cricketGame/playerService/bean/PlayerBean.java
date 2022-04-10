package com.tekion.cricketGame.playerService.bean;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("player")
@Document(collection = "player")
public class PlayerBean {

    @Column("playerId")
    private int playerId;

    @Column("teamId")
    private int teamId;

    @Column("playerName")
    private String playerName;

    @Column("createdTime")
    private Long createdTime;

    @Column("modifiedTime")
    private Long modifiedTime;

    @Column("isDeleted")
    private Boolean isDeleted;
}
