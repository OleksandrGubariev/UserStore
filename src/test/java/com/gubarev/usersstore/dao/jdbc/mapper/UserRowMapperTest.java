package com.gubarev.usersstore.dao.jdbc.mapper;

import com.gubarev.usersstore.entities.User;
import org.junit.Test;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserRowMapperTest {

    @Test
    public void testUserRowMapper() throws SQLException {
        UserRowMapper userRowMapper = new UserRowMapper();

        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getString("first_name")).thenReturn("FirstName");
        when(mockResultSet.getString("last_name")).thenReturn("LastName");
        LocalDate date = LocalDate.of(2000, 12,31);
        Date sqlDate = Date.valueOf(date);
        when(mockResultSet.getDate("date_of_birth")).thenReturn(sqlDate);
        when(mockResultSet.getDouble("salary")).thenReturn(20000.00);

        User actual = userRowMapper.mapRow(mockResultSet);

        assertNotNull(actual);
        assertEquals(1L, actual.getId());
        assertEquals("FirstName", actual.getFirstName());
        assertEquals("LastName", actual.getLastName());
        assertEquals(date, actual.getDateOfBirth());
        assertEquals(20000.00, actual.getSalary(), 0.1);
    }
}