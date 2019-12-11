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
        properties.putAll(System.getProperties());
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
