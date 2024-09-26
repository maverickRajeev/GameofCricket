package com.tekion.cricketGame.validator;

import com.tekion.cricketGame.cricketSeriesService.dto.SeriesRequestDto;
import com.tekion.cricketGame.enums.TypesOfMatch;

public class RequestValidator {

    public static boolean seriesRequestValidator(SeriesRequestDto newSeries){
        if(newSeries.getSeriesType().isEmpty())
            return false;

        if(TypesOfMatch.valueOf(newSeries.getSeriesType()) != TypesOfMatch.ODI || TypesOfMatch.valueOf(newSeries.getSeriesType()) != TypesOfMatch.T20 )
            return false;

        if(newSeries.getNumberOfMatches() <= 0)
            return false;

        if(newSeries.getTeam1Name().isEmpty() || newSeries.getTeam2Name().isEmpty())
            return false;

        return true;
    }


}
