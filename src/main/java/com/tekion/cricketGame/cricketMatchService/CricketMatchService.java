package com.tekion.cricketGame.cricketMatchService;

import org.springframework.stereotype.Service;

@Service
public interface CricketMatchService {
    void startCricketMatch(int numberOfOvers , int numberOfMatches , String team1Name , String team2Name);
}
