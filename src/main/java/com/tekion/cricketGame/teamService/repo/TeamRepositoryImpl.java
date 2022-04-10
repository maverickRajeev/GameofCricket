package com.tekion.cricketGame.teamService.repo;

import com.tekion.cricketGame.config.DatabaseConfig.ClassMapper;
import com.tekion.cricketGame.config.DatabaseConfig.ClassMapperMetaInfo;
import com.tekion.cricketGame.config.DatabaseConfig.SqlRepo;
import com.tekion.cricketGame.teamService.bean.TeamBean;
import com.tekion.cricketGame.teamService.dto.TeamMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@ClassMapperMetaInfo(getClassName = ClassMapper.TEAM_REPO)
public class TeamRepositoryImpl implements TeamRepository , SqlRepo {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void createTeam(String teamName){
        String sqlStatement = "INSERT INTO team(teamName , createdTime , modifiedTime , isDeleted) values(? , ? , ? , ?)";
        jdbcTemplate.update(sqlStatement , teamName , System.currentTimeMillis() , System.currentTimeMillis() , false);
    }

    @Override
    public Boolean ifCheckTeamExists(String teamName){
        String sqlStatement = "SELECT EXISTS(SELECT * FROM team WHERE teamName = ?)";
        return jdbcTemplate.queryForObject(sqlStatement , Boolean.class , teamName);
    }

    @Override
    public Boolean checkIfTeamIdExists(int teamId){
        String sqlStatement = "SELECT EXISTS(SELECT * FROM team WHERE teamId = ?)";
        return jdbcTemplate.queryForObject(sqlStatement , Boolean.class , teamId);
    }

    @Override
    public int getIdByTeamName(String teamName){
        String sqlStatement = "SELECT teamId FROM team WHERE teamName = ?";
        return jdbcTemplate.queryForObject(sqlStatement , Integer.class , teamName);
    }

    @Override
    public TeamBean getTeamDetails(int teamId){
        String sqlStatement = "SELECT * FROM team WHERE teamId = ?";
        return jdbcTemplate.queryForObject(sqlStatement , new TeamMapper() , teamId);
    }

}
