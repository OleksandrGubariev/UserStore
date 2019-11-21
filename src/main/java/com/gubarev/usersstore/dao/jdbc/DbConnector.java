package com.gubarev.usersstore.dao.jdbc;

import com.gubarev.usersstore.exception.ConnectDbException;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

class DbConnector {
    Connection createConnection() {

        String url;
        String user;
        String password;

        Properties properties = new Properties();

        try (InputStream inputStream =  getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if(inputStream==null){
                throw new NullPointerException("Config file is not found");
            }
            properties.load(inputStream);
            url = properties.getProperty("url");
            user = properties.getProperty("user");
            password = properties.getProperty("password");

        } catch (IOException e) {
            throw new RuntimeException("Cannot read propertiesPath for connection", e);
        }
        Connection connection;
        try {
            System.out.println(String.format("Connect to DB with params: %s %s", url, user));
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new ConnectDbException("Data base connection error. Check DB url, username and password.", ex);
        }







//
//        Connection connection;
//        try {
//            String dbUrl = System.getenv("JDBC_DATABASE_URL");
//            connection = DriverManager.getConnection(dbUrl);
//        } catch (SQLException ex) {
//            throw new ConnectDbException("Data base connection error", ex);
//        }
        return connection;
    }
}


