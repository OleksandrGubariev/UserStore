package com.gubarev.usersstore;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
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
        Map<String, String> mapDbProperty = parseUri(dbUri);
        Properties properties = new Properties();
        properties.setProperty("PORT", serverPortEnv);
        properties.setProperty("JDBC_SERVER", mapDbProperty.get("JDBC_SERVER"));
        properties.setProperty("JDBC_PORT", mapDbProperty.get("JDBC_PORT"));
        properties.setProperty("JDBC_DATABASE", mapDbProperty.get("JDBC_DATABASE"));
        properties.setProperty("JDBC_LOGIN", System.getenv("JDBC_DATABASE_USERNAME"));
        properties.setProperty("JDBC_PASSWORD", System.getenv("JDBC_DATABASE_PASSWORD"));
        return properties;
    }

    public Map<String, String> parseUri(String dbUri){
        String dbParameters = dbUri.substring(dbUri.indexOf("@") + 1);
        Map<String, String> mapDbProperty = new HashMap<>();
        mapDbProperty.put("JDBC_SERVER",dbParameters.substring(0, dbParameters.indexOf(":")));
        mapDbProperty.put("JDBC_PORT", dbParameters.substring(dbParameters.indexOf(":")+1, dbParameters.indexOf("/")));
        mapDbProperty.put("JDBC_DATABASE", dbParameters.substring(dbParameters.indexOf("/")+1));
        return mapDbProperty;
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
