package com.gubarev.usersstore.dao.jdbc;

import com.gubarev.usersstore.exception.ConnectDbException;

import java.sql.*;


public class DbConnector {
    public Connection createConnection(){
        String url="jdbc:mysql://localhost:3306/giraffe";
        String user= "testuser";
        String password="testpassword";
        Connection connection;
        try {
            System.out.println(String.format("Connect to DB with params: %s %s", url, user));
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new ConnectDbException("Data base connection error. Check DB url, username and password.", ex);
        }
        return connection;
    }
}


