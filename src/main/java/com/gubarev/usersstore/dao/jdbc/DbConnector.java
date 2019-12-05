package com.gubarev.usersstore.dao.jdbc;

import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public class DbConnector implements DataSource {

    @Override
    public Connection getConnection() {
        String serverName;
        int port;
        String dbName;
        String user;
        String password;

        Properties properties = new Properties();
        try (FileInputStream fileInputStream = new FileInputStream("src/main/resources/config.properties")) {
            properties.load(fileInputStream);
            serverName = properties.getProperty("serverName");
            String portString = properties.getProperty("port");
            port = Integer.parseInt(portString);
            dbName = properties.getProperty("dbName");
            user = properties.getProperty("user");
            password = properties.getProperty("password");

        } catch (IOException e) {
            throw new RuntimeException("Cannot read propertiesPath for connection", e);
        }
        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setServerName(serverName);
        source.setPortNumber(port);
        source.setDatabaseName(dbName);
        source.setUser(user);
        source.setPassword(password);

        try {
            return source.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Data base connection error");
        }
    }

    @Override
    public Connection getConnection(String s, String s1) throws SQLException {
        return null;
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    @Override
    public void setLogWriter(PrintWriter printWriter) throws SQLException {

    }

    @Override
    public void setLoginTimeout(int i) throws SQLException {

    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

    @Override
    public <T> T unwrap(Class<T> aClass) throws SQLException {
        return null;
    }

    @Override
    public boolean isWrapperFor(Class<?> aClass) throws SQLException {
        return false;
    }
}


