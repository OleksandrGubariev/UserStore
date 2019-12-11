package com.gubarev.usersstore.dao.jdbc;

import org.postgresql.ds.PGPoolingDataSource;

import javax.sql.DataSource;
import java.util.Properties;

public class DataSourceFactory {

    public DataSource getDataSource(Properties properties) {

        String dbHost = properties.getProperty("JDBC_SERVER");
        String portString = properties.getProperty("JDBC_PORT");
        int dbPort = Integer.parseInt(portString);
        String dbName = properties.getProperty("JDBC_DATABASE");
        String dbLogin = properties.getProperty("JDBC_LOGIN");
        String dbPassword = properties.getProperty("JDBC_PASSWORD");

        PGPoolingDataSource source = new PGPoolingDataSource();
        source.setServerName(dbHost);
        source.setPortNumber(dbPort);
        source.setDatabaseName(dbName);
        source.setUser(dbLogin);
        source.setPassword(dbPassword);

        return source;
    }
}


