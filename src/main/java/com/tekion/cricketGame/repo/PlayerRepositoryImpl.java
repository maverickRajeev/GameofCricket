package com.tekion.cricketGame.repo;

import com.tekion.cricketGame.service.ConnectionService;
import com.tekion.cricketGame.service.ConnectionServiceImpl;

import java.sql.*;

public class PlayerRepositoryImpl implements PlayerRepository {
    ConnectionService connectionService = new ConnectionServiceImpl();
    Connection connection;

    public PlayerRepositoryImpl() {
        try {
            connection = connectionService.getConnection();
        }catch(SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Boolean ifCheckPlayerExists(int teamId , String playerName){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT playerId , teamId , playerName FROM player WHERE teamId = ? AND playerName = ?");
            statement.setInt(1 , teamId);
            statement.setString(2 , playerName);
            ResultSet rs = statement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void createPlayer(int teamId , String playerName){
        try {
            PreparedStatement statement = connection.prepareStatement("INSERT INTO player(teamId , playerName) values(? , ?)");
            statement.setInt(1 , teamId);
            statement.setString(2 , playerName);
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getIdByTeamIdAndPlayerName(int teamId , String playerName){
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT playerId WHERE teamId = ? AND playerName = ? ");
            statement.setInt(1 , teamId);
            statement.setString(2 , playerName);
            ResultSet rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);
        }catch (SQLException e){
            e.printStackTrace();
            return -1;
        }
    }
}
