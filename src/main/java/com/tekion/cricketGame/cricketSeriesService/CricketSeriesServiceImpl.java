package com.tekion.cricketGame.cricketSeriesService;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import com.tekion.cricketGame.cricketSeriesService.dto.CricketSeriesDto;
import com.tekion.cricketGame.cricketSeriesService.dto.SeriesRequestDto;
import com.tekion.cricketGame.cricketSeriesService.repo.CricketSeriesRepo;
import com.tekion.cricketGame.enums.TypesOfMatch;
import com.tekion.cricketGame.teamService.TeamService;
import com.tekion.cricketGame.teamService.dto.TeamDto;
import com.tekion.cricketGame.utils.BeanMapperFromDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
        int newCreatedSeriesId = this.createSeriesDb(cricketSeries);
        this.playSeries(cricketSeries , newCreatedSeriesId);
        return this.getSeriesDetails(newCreatedSeriesId);
    }

    @Override
    public boolean checkIfSeriesExists(int seriesId){
        return cricketSeriesRepo.checkSeriesId(seriesId);
    }

    @Cacheable(cacheNames = "seriesCache" , key = "#seriesId")
    @Override
    public CricketSeriesBean getSeriesDetails(int seriesId){
        return cricketSeriesRepo.getSeriesDetailsById(seriesId);
    }

    private CricketSeriesDto setTeamInfo(CricketSeriesDto cricketSeries , String team1Name , String team2Name) {
        cricketSeries.setSeriesTeam1(new TeamDto(teamService.loadTeamDetails(team1Name)));
        cricketSeries.setSeriesTeam2(new TeamDto(teamService.loadTeamDetails(team2Name)));
        return cricketSeries;
    }

    private void playSeries(CricketSeriesDto cricketSeries , int seriesId){

         for(int i = 0 ; i < cricketSeries.getNumberOfMatches() ; i++){
             TeamDto winnerTeam = cricketMatchService.startCricketMatch(cricketSeries , seriesId);
             if(winnerTeam == cricketSeries.getSeriesTeam1())
                 cricketSeries.setNumberOfMatchesWonByTeam1();
             else
                 cricketSeries.setNumberOfMatchesWonByTeam2();

             cricketSeries = this.setTeamInfo(cricketSeries , cricketSeries.getSeriesTeam1().getTeamName() , cricketSeries.getSeriesTeam2().getTeamName());
         }

         this.updateSeriesDb(cricketSeries , seriesId);
    }

    private int createSeriesDb(CricketSeriesDto cricketSeries){
        CricketSeriesBean newSeries = beanMapperFromDto.mapSeriesDtoToBean(cricketSeries);
        return cricketSeriesRepo.createSeries(newSeries);
    }

    @CachePut(cacheNames = "seriesCache" , key = "#seriesId")
    private void updateSeriesDb(CricketSeriesDto cricketSeries , int seriesId){
        CricketSeriesBean newSeries = beanMapperFromDto.mapSeriesDtoToBean(cricketSeries);
        cricketSeriesRepo.updateSeriesByMatch(newSeries , seriesId);
    }

}
