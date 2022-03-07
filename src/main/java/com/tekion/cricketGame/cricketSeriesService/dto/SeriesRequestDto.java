package com.tekion.cricketGame.cricketSeriesService.dto;

import lombok.Data;

@Data
public class SeriesRequestDto {
    private int overs;
    private int numberOfMatches;
    private String team1Name;
    private String team2Name;
}
