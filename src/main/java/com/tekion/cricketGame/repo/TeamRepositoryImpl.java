package com.tekion.cricketGame.repo;

import com.tekion.cricketGame.service.ConnectionService;
import com.tekion.cricketGame.service.ConnectionServiceImpl;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.*;

@Repository
public class TeamRepositoryImpl implements TeamRepository{
    private JdbcTemplate jdbcTemplate;

    public TeamRepositoryImpl() {

    }

    public Boolean ifCheckTeamExists(String teamName){
        String sqlStatement = "SELECT teamId , teamName FROM team WHERE teamName = ?";
        int res = jdbcTemplate.
        try {
            PreparedStatement statement = connection.prepareStatement();
            statement.setString(1 , teamName);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createTeam(String teamName){
        String sqlStatement = "INSERT INTO team(teamName) values(?)";
        int res = jdbcTemplate.update(sqlStatement , teamName);
        System.out.println(res);
//        try {
//            PreparedStatement statement = connection.prepareStatement();
//            statement.setString(1 ,teamName);
//            statement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    public int getIdByTeamName(String teamName){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT teamId from team WHERE teamName = ? ");
            statement.setString(1, teamName);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
}
