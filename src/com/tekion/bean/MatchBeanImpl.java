package com.tekion.bean;

import com.mysql.jdbc.Driver;
import com.tekion.dto.Match;

import java.sql.*;


public class MatchBeanImpl implements MatchBean {

    public static Connection getConnection() throws SQLException {
        DriverManager.registerDriver(new Driver());
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cricket_stats" ,
                                                            "root" , "Maverick@9845");
        System.out.println("MySQL Connection Established");
        return connection;
    }

    public void getMatchDetails() {

    }
}
