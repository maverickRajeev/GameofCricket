package com.tekion.cricketGame.cricketSeriesService;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import com.tekion.cricketGame.cricketSeriesService.dto.BeanMapperFromDto;
import com.tekion.cricketGame.cricketSeriesService.dto.CricketSeriesDto;
import com.tekion.cricketGame.cricketSeriesService.dto.SeriesRequestDto;
import com.tekion.cricketGame.cricketSeriesService.repo.CricketSeriesRepo;
import com.tekion.cricketGame.enums.TypesOfMatch;
import com.tekion.cricketGame.teamService.TeamService;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CricketSeriesServiceImpl implements CricketSeriesService {

    private final CricketSeriesRepo cricketSeriesRepo;
    private final CricketMatchService cricketMatchService;
    private final TeamService teamService;
    private final BeanMapperFromDto beanMapperFromDto;

    @Autowired
    public CricketSeriesServiceImpl(CricketSeriesRepo cricketSeriesRepo , CricketMatchService cricketMatchService , TeamService teamService , BeanMapperFromDto beanMapperFromDto ){
        this.cricketSeriesRepo = cricketSeriesRepo;
        this.cricketMatchService = cricketMatchService;
        this.teamService = teamService;
        this.beanMapperFromDto = beanMapperFromDto;
    }

    @Override
    public CricketSeriesBean beginSeries(SeriesRequestDto newSeries) {
        int matchOvers = TypesOfMatch.valueOf(newSeries.getSeriesType()).getOversForMatchType();
        int numberOfMatches = newSeries.getNumberOfMatches();
        CricketSeriesDto cricketSeries = new CricketSeriesDto(matchOvers ,  numberOfMatches);
        cricketSeries = this.setTeamInfo(cricketSeries , newSeries.getTeam1Name() , newSeries.getTeam2Name());
        cricketSeries = this.playSeries(cricketSeries);
        return this.updateSeriesDb(cricketSeries);
    }

    @Override
    public boolean checkIfSeriesExists(int seriesId){
        return cricketSeriesRepo.checkSeriesId(seriesId);
    }

    @Override
    public CricketSeriesBean getSeriesDetails(int seriesId){
        return cricketSeriesRepo.getSeriesDetailsById(seriesId);
    }

    private CricketSeriesDto setTeamInfo(CricketSeriesDto cricketSeries , String team1Name , String team2Name) {
        cricketSeries.setSeriesTeam1(new TeamDto(teamService.loadTeamDetails(team1Name)));
        cricketSeries.setSeriesTeam2(new TeamDto(teamService.loadTeamDetails(team2Name)));
        return cricketSeries;
    }

    private CricketSeriesDto playSeries(CricketSeriesDto cricketSeries){

         for(int i = 0 ; i < cricketSeries.getNumberOfMatches() ; i++){
             TeamDto winnerTeam = cricketMatchService.startCricketMatch(cricketSeries);
             if(winnerTeam == cricketSeries.getSeriesTeam1())
                 cricketSeries.setNumberOfMatchesWonByTeam1();
             else
                 cricketSeries.setNumberOfMatchesWonByTeam2();
         }

         return cricketSeries;
    }

    private CricketSeriesBean updateSeriesDb(CricketSeriesDto cricketSeries){
        CricketSeriesBean newSeries = beanMapperFromDto.mapSeriesDtoToBean(cricketSeries);
        int generatedSeriesId = cricketSeriesRepo.createSeries(newSeries);
        return cricketSeriesRepo.getSeriesDetailsById(generatedSeriesId);
    }

}
