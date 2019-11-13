package com.gubarev.usersstore.dao.jdbc.mapper;


import com.gubarev.usersstore.entity.User;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class RowsMapperTest {

    @Test
    public void testMapRow() throws SQLException {
        RowsMapper rowsMapper = new RowsMapper();

        ResultSet mockResultSet = mock(ResultSet.class);

        when(mockResultSet.getLong("id")).thenReturn(1L);
        when(mockResultSet.getString("first_name")).thenReturn("FirstName");
        when(mockResultSet.getString("last_name")).thenReturn("LastName");
        LocalDate date = LocalDate.of(2000, 12,31);
        Timestamp sqlDate = Timestamp.valueOf(date.atTime(LocalTime.MIDNIGHT));
        when(mockResultSet.getTimestamp("date_of_birth")).thenReturn(sqlDate);
        when(mockResultSet.getDouble("salary")).thenReturn(20000.00);

        User actual = rowsMapper.userRowMapper(mockResultSet);

        assertNotNull(actual);
        assertEquals(1L, actual.getId());
        assertEquals("FirstName", actual.getFirstName());
        assertEquals("LastName", actual.getLastName());
        assertEquals(date, actual.getDateOfBirth());
        assertEquals(20000.00, actual.getSalary(), 0.1);
    }
}