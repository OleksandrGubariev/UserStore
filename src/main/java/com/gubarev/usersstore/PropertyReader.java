package com.gubarev.usersstore;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    public Properties getProperties() {
        String prodEnv = System.getenv("ENV");
        if (prodEnv!=null&&prodEnv.equalsIgnoreCase("prod")) {
            return getProductionProperties();
        }
        return getDevProperties();
    }

    private Properties getProductionProperties() {
        String serverPortEnv = System.getenv("SERVER_PORT");
        if(serverPortEnv == null){
            throw new RuntimeException("Set environment variable SERVER_PORT");
        }
        Properties properties = new Properties();
        properties.setProperty("SERVER_PORT", serverPortEnv);
        properties.setProperty("JDBC_SERVER", System.getenv("JDBC_SERVER"));
        properties.setProperty("JDBC_PORT", System.getenv("JDBC_PORT"));
        properties.setProperty("JDBC_DATABASE", System.getenv("JDBC_DATABASE"));
        properties.setProperty("JDBC_LOGIN", System.getenv("JDBC_LOGIN"));
        properties.setProperty("JDBC_PASSWORD", System.getenv("JDBC_PASSWORD"));
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
