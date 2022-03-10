package com.tekion.cricketGame.cricketSeriesService.dto;

import com.tekion.cricketGame.cricketSeriesService.bean.CricketSeriesBean;
import com.tekion.cricketGame.teamService.repo.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanMapperFromDto {

    private final TeamRepository teamRepo;

    @Autowired
    public BeanMapperFromDto(TeamRepository teamRepo){
        this.teamRepo = teamRepo;
    }

    public CricketSeriesBean mapSeriesDtoToBean(CricketSeriesDto cricketSeries){

        CricketSeriesBean newSeries = new CricketSeriesBean();
        newSeries.setSeriesType(cricketSeries.getNumberOfOvers());
        newSeries.setNumberOfMatches(cricketSeries.getNumberOfMatches());
        newSeries.setTeam1Id(teamRepo.getIdByTeamName(cricketSeries.getSeriesTeam1().getTeamName()));
        newSeries.setTeam2Id(teamRepo.getIdByTeamName(cricketSeries.getSeriesTeam2().getTeamName()));
        newSeries.setNumberOfMatchesWonByTeam1(cricketSeries.getNumberOfMatchesWonByTeam1());
        newSeries.setNumberOfMatchesWonByTeam2(cricketSeries.getNumberOfMatchesWonByTeam2());
        newSeries.setNumberOfMatchesTied(cricketSeries.getNumberOfMatchesTied());

        return newSeries;
    }
}
