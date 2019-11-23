package com.gubarev.usersstore.dao.jdbc;

import com.gubarev.usersstore.exception.ConnectDbException;

import java.sql.*;

class DbConnector {
    Connection createConnection() {
        try (Connection connection = DriverManager.getConnection(System.getenv("JDBC_DATABASE_URL"))) {
            return connection;
        } catch (SQLException ex) {
            throw new ConnectDbException("Data base connection error", ex);
        }
    }
}


