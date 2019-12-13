package com.gubarev.usersstore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    public Properties getProperties() {
        String prodEnv = System.getenv("ENV");
        if (prodEnv != null && prodEnv.equalsIgnoreCase("prod")) {
            return getProductionProperties();
        }
        return getDevProperties();
    }

    private Properties getProductionProperties() {
        String serverPortEnv = System.getenv("PORT");
        String dbUri = System.getenv("DATABASE_URL");
        String jdbcParameters = dbUri.substring(dbUri.indexOf("@")+1);
        String jdbcDbServer = jdbcParameters.substring(0,jdbcParameters.indexOf(":"));
        String jdbcDbPort = jdbcParameters.substring(1,jdbcParameters.indexOf("/"));
        String jdbcDb = jdbcParameters.substring(1);


        Properties properties = new Properties();
        properties.setProperty("PORT", serverPortEnv);
        properties.setProperty("JDBC_SERVER", jdbcDbServer);
        properties.setProperty("JDBC_PORT", jdbcDbPort);
        properties.setProperty("JDBC_DATABASE", jdbcDb);
        properties.setProperty("JDBC_LOGIN", System.getenv("JDBC_DATABASE_USERNAME"));
        properties.setProperty("JDBC_PASSWORD", System.getenv("JDBC_DATABASE_PASSWORD"));
        return properties;
    }

    private Properties getDevProperties() {
        Properties properties = new Properties();
        try (InputStream inputStream = PropertyReader.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (inputStream == null) {
                throw new RuntimeException("Config file isn't found");
            }
            properties.load(inputStream);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Cannot read propertiesPath for connection", e);
        }
    }
}
