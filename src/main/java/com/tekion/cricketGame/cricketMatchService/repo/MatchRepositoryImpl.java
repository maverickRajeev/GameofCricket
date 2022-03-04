package com.tekion.cricketGame.cricketMatchService.repo;

import com.tekion.cricketGame.cricketMatchService.dto.MatchDto;
import com.tekion.cricketGame.service.ConnectionService;
import com.tekion.cricketGame.service.ConnectionServiceImpl;

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

    void insertMatchData(MatchDto match){

    }







}
