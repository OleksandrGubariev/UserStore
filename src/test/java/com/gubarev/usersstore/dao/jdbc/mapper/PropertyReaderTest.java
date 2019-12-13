package com.gubarev.usersstore.dao.jdbc.mapper;

import com.gubarev.usersstore.PropertyReader;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

public class PropertyReaderTest {
    @Test
    public void testParseUri() {
        String dbUri = "postgres://wpjifjglnmltmx:a2d16c0c2fffeadb88941f45c41f19375332c44dbd3bf9363da1c17d2e0bb33e@ec2-54-83-9-169.compute-1.amazonaws.com:5432/d2ett8cej1jnfp";

        PropertyReader propertyReader = new PropertyReader();
        Map<String, String> mapDbProperty = propertyReader.parseUri(dbUri);

        assertEquals("ec2-54-83-9-169.compute-1.amazonaws.com", mapDbProperty.get("JDBC_SERVER"));
        assertEquals("5432", mapDbProperty.get("JDBC_PORT"));
        assertEquals("d2ett8cej1jnfp", mapDbProperty.get("JDBC_DATABASE"));
    }
}
