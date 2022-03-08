package com.tekion.cricketGame.cricketSeriesService;

import com.tekion.cricketGame.cricketMatchService.CricketMatchService;
import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
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

    @Autowired
    public CricketSeriesServiceImpl(CricketSeriesRepo cricketSeriesRepo , CricketMatchService cricketMatchService , TeamService teamService){
        this.cricketSeriesRepo = cricketSeriesRepo;
        this.cricketMatchService = cricketMatchService;
        this.teamService = teamService;
    }

    @Override
    public void beginSeries(SeriesRequestDto newSeries) {
        int matchOvers = TypesOfMatch.valueOf(newSeries.getSeriesType()).getOversForMatchType();
        int numberOfMatches = newSeries.getNumberOfMatches();
        CricketSeriesDto cricketSeries = new CricketSeriesDto(matchOvers ,  numberOfMatches);
        this.setTeamInfo(cricketSeries , newSeries.getTeam1Name() , newSeries.getTeam2Name());
        this.playSeries(cricketSeries);
    }

    @Override
    public boolean checkIfSeriesExists(int seriesId){
        return cricketSeriesRepo.checkSeriesId(seriesId);
    }

    @Override
    public CricketSeriesBean getSeriesDetails(int seriesId){
        return cricketSeriesRepo.getSeriesDetailsById(seriesId);
    }

    private void setTeamInfo(CricketSeriesDto cricketSeries , String team1Name , String team2Name) {
        cricketSeries.setSeriesTeam1(new TeamDto(teamService.loadTeamDetails(team1Name)));
        cricketSeries.setSeriesTeam2(new TeamDto(teamService.loadTeamDetails(team2Name)));
    }

    private void playSeries(CricketSeriesDto cricketSeries){
         for(int i = 0 ; i < cricketSeries.getNumberOfMatches() ; i++){
             cricketMatchService.startCricketMatch(cricketSeries.getNumberOfOvers() , cricketSeries.getSeriesTeam1() , cricketSeries.getSeriesTeam2());
         }
    }

}
