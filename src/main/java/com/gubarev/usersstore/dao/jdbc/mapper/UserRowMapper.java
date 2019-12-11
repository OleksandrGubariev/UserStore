package com.gubarev.usersstore.dao.jdbc.mapper;

import com.gubarev.usersstore.entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;

public class UserRowMapper {
    public User mapRow(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getLong("id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));

        Timestamp dbDateOfBirth = new Timestamp(resultSet.getDate("date_of_birth").getTime());
        LocalDate dateOfBirth = dbDateOfBirth.toLocalDateTime().toLocalDate();
        user.setDateOfBirth(dateOfBirth);

        user.setSalary(resultSet.getDouble("salary"));

        return user;
    }
}
