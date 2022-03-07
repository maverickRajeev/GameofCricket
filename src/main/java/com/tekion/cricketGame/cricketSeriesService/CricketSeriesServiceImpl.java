package com.tekion.cricketGame.cricketSeriesService;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.cricketSeriesService.dto.CricketSeriesDto;
import com.tekion.cricketGame.cricketSeriesService.dto.SeriesRequestDto;
import com.tekion.cricketGame.cricketSeriesService.repo.CricketSeriesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CricketSeriesServiceImpl implements CricketSeriesService {

    private final CricketSeriesRepo cricketSeriesRepo;
    private final CricketMatchService cricketMatchService;

    @Autowired
    public CricketSeriesServiceImpl(CricketSeriesRepo cricketSeriesRepo , CricketMatchService cricketMatchService){
        this.cricketSeriesRepo = cricketSeriesRepo;
        this.cricketMatchService = cricketMatchService;
    }

    @Override
    public void beginSeries(SeriesRequestDto newSeries) {
        int matchOvers = newSeries.getOvers();
        int numberOfMatches = newSeries.getNumberOfMatches();
        CricketSeriesDto cricketSeries = new CricketSeriesDto(matchOvers ,  numberOfMatches);
        this.playSeries(matchOvers , numberOfMatches , newSeries.getTeam1Name() , newSeries.getTeam2Name());
    }

    private void playSeries(int matchOvers , int numberOfMatches , String team1Name , String team2Name){
         for(int i = 0 ; i < numberOfMatches ; i++){
             cricketMatchService.startCricketMatch(matchOvers , numberOfMatches , team1Name , team2Name);
         }
    }

}
