package com.tekion.cricketGame.teamService.bean;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("team")
@Document(collection = "team")
public class TeamBean {

    @Column("teamId")
    private int teamId;

    @Column("teamName")
    private String teamName;

    @Column("createdTime")
    private Long createdTime;

    @Column("modifiedTime")
    private Long modifiedTime;

    @Column("isDeleted")
    private Boolean isDeleted;

}
