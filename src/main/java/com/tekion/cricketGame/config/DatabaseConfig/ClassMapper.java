package com.tekion.cricketGame.config.DatabaseConfig;

import com.tekion.cricketGame.cricketMatchService.repo.CricketMatchRepo;
import com.tekion.cricketGame.cricketSeriesService.repo.CricketSeriesRepo;
import com.tekion.cricketGame.playerService.repo.PlayerRepository;
import com.tekion.cricketGame.scoreBoardService.repo.ScoreBoardRepository;
import com.tekion.cricketGame.teamService.repo.TeamRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClassMapper {

    CRICKET_SERIES_REPO(CricketSeriesRepo.class),
    CRICKET_MATCH_REPO(CricketMatchRepo.class),
    PLAYER_REPO(PlayerRepository.class),
    TEAM_REPO(TeamRepository.class),
    SCOREBOARD_REPO(ScoreBoardRepository.class);

    private final Class<?> classRepo;
}
