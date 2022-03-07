package com.tekion.cricketGame.playerService.repo;

import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository {
    void createPlayer(int teamId , String playerName);
    Boolean ifCheckPlayerExists(int teamId , String playerName);
    int getIdByTeamIdAndPlayerName(int teamId , String playerName);
}
