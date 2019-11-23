package com.gubarev.usersstore.dao.jdbc;

import com.gubarev.usersstore.exception.ConnectDbException;

import java.sql.*;

class DbConnector {
    Connection createConnection() {
        Connection connection;
        try {
            String dbUrl = System.getenv("JDBC_DATABASE_URL");
            connection = DriverManager.getConnection(dbUrl);
        } catch (SQLException ex) {
            throw new ConnectDbException("Data base connection error", ex);
        }
        return connection;
    }
}


