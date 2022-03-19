package com.tekion.cricketGame.validator;

import com.tekion.cricketGame.cricketSeriesService.dto.SeriesRequestDto;
import com.tekion.cricketGame.enums.TypesOfMatch;

public class RequestValidator {

    public static boolean seriesRequestValidator(SeriesRequestDto newSeries){
        if(newSeries.getSeriesType().isEmpty()) {
            return false;
        }

        if(!TypesOfMatch.TypeOfMatchExists(newSeries.getSeriesType())) {
            return false;
        }

        if(newSeries.getNumberOfMatches() <= 0) {
            return false;
        }

        if(newSeries.getTeam1Name().isEmpty() || newSeries.getTeam2Name().isEmpty()) {
            return false;
        }

        return true;
    }


}
