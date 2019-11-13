package com.gubarev.usersstore.dao.jdbc;

import com.gubarev.usersstore.dao.jdbc.mapper.RowsMapper;
import com.gubarev.usersstore.entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao {
    private static final String GET_ALL_USERS = "SELECT id, first_name, last_name, date_of_birth, salary FROM User;";
    private static final String INSERT_USER = "INSERT User (first_name, last_name, date_of_birth, salary) VALUES (?, ?, ?, ?);";
    private static final String DELETE_USER = "DELETE FROM User WHERE id=?";

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        PreparedStatement preparedStatement = getStatement(GET_ALL_USERS);
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            RowsMapper rowsMapper = new RowsMapper();
            while (resultSet.next()) {
                User user = rowsMapper.userRowMapper(resultSet);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void insertUser(User user) {
        PreparedStatement preparedStatement = getStatement(INSERT_USER);
        try {
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            LocalDate dateOfBirth = user.getDateOfBirth();
            Timestamp sqlDateOfBirth = Timestamp.valueOf(dateOfBirth.atStartOfDay());
            preparedStatement.setTimestamp(3, sqlDateOfBirth);
            preparedStatement.setDouble(4, user.getSalary());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteUser(long id) {
        PreparedStatement preparedStatement = getStatement(DELETE_USER);
        try {
            preparedStatement.setDouble(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    PreparedStatement getStatement(String request) {
        Connection connection = new DbConnector().createConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }
}