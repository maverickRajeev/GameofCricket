package com.tekion.repo;

import com.tekion.service.ConnectionService;
import com.tekion.service.ConnectionServiceImpl;

import java.sql.*;

public class TeamRepositoryImpl implements TeamRepository{
    ConnectionService connectionService = new ConnectionServiceImpl();
    Connection connection;

    public TeamRepositoryImpl() {
        try {
            connection = connectionService.getConnection();
        }catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Boolean ifCheckTeamExists(String teamName){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT teamId , teamName FROM team WHERE teamName = ?");
            statement.setString(1 , teamName);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createTeam(String teamName){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO team(teamName) values(?)");
            statement.setString(1 ,teamName);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
