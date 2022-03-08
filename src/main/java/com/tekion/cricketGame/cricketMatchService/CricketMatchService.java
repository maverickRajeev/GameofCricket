package com.tekion.cricketGame.cricketMatchService;

import com.tekion.cricketGame.teamService.dto.TeamDto;
import org.springframework.stereotype.Service;

@Service
public interface CricketMatchService {
    void startCricketMatch(int numberOfOvers , TeamDto team1 , TeamDto team2);
}
