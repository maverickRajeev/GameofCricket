package com.tekion.cricketGame.scoreBoardService.bean;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("match_scoreboard")
public class MatchScoreBoardBean {

      @Column("scoreBoardId")
      private int scoreBoardId;

      @Column("matchId")
      private int matchId;

      @Column("teamBattingFirstId")
      private int teamBattingFirstId;

      @Column("teamFieldingFirstId")
      private int teamFieldingFirstId;

      @Column("firstInningScore")
      private int firstInningScore;

      @Column("firstInningWickets")
      private int firstInningWickets;

      @Column("firstInningBallsCompleted")
      private int firstInningBallsCompleted;

      @Column("secondInningScore")
      private int secondInningScore;

      @Column("secondInningWickets")
      private int secondInningWickets;

      @Column("secondInningBallsCompleted")
      private int secondInningBallsCompleted;

      @Column("createdTime")
      private Long createdTime;

      @Column("modifiedTime")
      private Long modifiedTime;

      @Column("isDeleted")
      private Boolean isDeleted;

}
