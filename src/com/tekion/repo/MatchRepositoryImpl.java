package com.tekion.repo;

import com.tekion.dto.Match;
import com.tekion.service.ConnectionService;
import com.tekion.service.ConnectionServiceImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class MatchRepositoryImpl implements MatchRepository {
    ConnectionService connectionService = new ConnectionServiceImpl();
    Connection connection;

    public MatchRepositoryImpl() {
       try {
         connection = connectionService.getConnection();
       }catch(SQLException sqle) {
           System.out.println(sqle);
       }catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
    }

    void insertMatchData(Match match){

    }







}
