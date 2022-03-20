package com.tekion.cricketGame.cricketMatchService.bean;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("match")
public class CricketMatchBean {

    @Column("seriesId")
    private int seriesId;

    @Column("matchId")
    private int matchId;

    @Column("tossWinnerTeamId")
    private int tossWinnerTeamId;

    @Column("winnerTeamId")
    private int winnerTeamId;

    @Column("winningMarginByRuns")
    private int winningMarginByRuns;

    @Column("winningMarginByWickets")
    private int winningMarginByWickets;

    @Column("matchTied")
    private Boolean matchTied;

    @Column("createdTime")
    private Long createdTime;

    @Column("modifiedTime")
    private Long modifiedTime;

    @Column("isDeleted")
    private Boolean isDeleted;

}
