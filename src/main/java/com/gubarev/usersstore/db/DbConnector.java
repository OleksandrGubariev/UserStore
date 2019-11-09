package com.gubarev.usersstore.db;

import com.gubarev.usersstore.exception.ConnectDbException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DbConnector {
    public Connection createConnection(){
        String url="jdbc:mysql://localhost:3306/giraffe";
        String user= "testuser";
        String password="testpassword";
    //    String propertiesPath = System.getProperty("properties");
        Connection connection;
//        if (propertiesPath == null) {
//            url = System.getProperty("url");
//            user = System.getProperty("user");
//            password = System.getProperty("password");
//        } else {
//            Properties properties = new Properties();
//            try (FileInputStream fileInputStream = new FileInputStream(propertiesPath)) {
//                properties.load(fileInputStream);
//                url = properties.getProperty("url");
//                user = properties.getProperty("user");
//                password = properties.getProperty("password");
//
//            } catch (IOException e) {
//                throw new RuntimeException("Cannot read propertiesPath for connection", e);
//            }
//        }
        try {
            System.out.println(String.format("Connect to DB with params: %s %s", url, user));
            connection = DriverManager.getConnection(url, user, password);
         //   System.out.println("Connection OK...Input your query.");
        } catch (SQLException ex) {
            throw new ConnectDbException("Data base connection error. Check DB url, username and password.", ex);
        }
        return connection;

    }
}


