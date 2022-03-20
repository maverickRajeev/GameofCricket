package com.tekion.cricketGame.cricketSeriesService.dto;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

@Data
public class SeriesRequestDto {

    @JsonView
    private String seriesType;

    @JsonView
    private int numberOfMatches;

    @JsonView
    private String team1Name;

    @JsonView
    private String team2Name;

}
