package com.tekion.cricketGame.teamService.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TeamRepositoryImpl implements TeamRepository {

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
    public int getIdByTeamName(String teamName){
        String sqlStatement = "SELECT teamId FROM team WHERE teamName = ?";
        return jdbcTemplate.queryForObject(sqlStatement , Integer.class , teamName);
    }
}
