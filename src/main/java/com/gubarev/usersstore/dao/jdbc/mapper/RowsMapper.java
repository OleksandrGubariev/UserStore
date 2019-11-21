package com.gubarev.usersstore.dao.jdbc.mapper;

import com.gubarev.usersstore.entity.User;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class RowsMapper {
    public User userRowMapper(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));

        Date sqlDateOfBirth = resultSet.getDate("date_of_birth");
        LocalDate dateOfBirth = sqlDateOfBirth.toLocalDate();
        user.setDateOfBirth(dateOfBirth);

        user.setSalary(resultSet.getDouble("salary"));

        return user;
    }
}
