package com.tekion.cricketGame.service;

import java.sql.*;

public class ConnectionServiceImpl implements ConnectionService{

    public Connection getConnection() throws SQLException, ClassNotFoundException {
         Class.forName("com.mysql.cj.jdbc.Driver");
         Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cricket_stats" ,
                                "root" , "rootpassword");
         System.out.println("Connection established to cricket_stats.");
         return connection;
    }
}
