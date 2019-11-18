package com.gubarev.usersstore.dao.jdbc;

import com.gubarev.usersstore.dao.jdbc.mapper.RowsMapper;
import com.gubarev.usersstore.entity.User;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao {
    private static final String GET_ALL_USERS = "SELECT id, first_name, last_name, date_of_birth, salary FROM Users ORDER BY id ASC;";
    private static final String INSERT_USER = "INSERT INTO Users (first_name, last_name, date_of_birth, salary) VALUES (?, ?, ?, ?);";
    private static final String DELETE_USER = "DELETE FROM Users WHERE id=?;";
    private static final String GET_USER_BY_ID = "SELECT id, first_name, last_name, date_of_birth, salary FROM Users WHERE id=?;";
    private static final String UPDATE_USER_BY_ID = "UPDATE Users SET first_name=?, last_name=?, date_of_birth=?, salary=? WHERE id=?;";
    private static final String SEARCH_USER = "SELECT id, first_name, last_name, date_of_birth, salary FROM Users WHERE LOWER(first_name) LIKE LOWER(?) OR LOWER(last_name) LIKE LOWER(?) ORDER BY id ASC;";

    public List<User> getAllUsers() {
        PreparedStatement preparedStatement = getStatement(GET_ALL_USERS);
        return getListUsers(preparedStatement);
    }

    public void insertUser(User user) {
        try (PreparedStatement preparedStatement = getStatement(INSERT_USER)) {
            fillPreparedStatement(preparedStatement, user);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(long id) {
        try (PreparedStatement preparedStatement = getStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(long id) {
        PreparedStatement preparedStatement = getStatement(GET_USER_BY_ID);
        User user = null;
        try {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            RowsMapper rowsMapper = new RowsMapper();
            while (resultSet.next()) {
                user = rowsMapper.userRowMapper(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void editUser(User user) {
        try (PreparedStatement preparedStatement = getStatement(UPDATE_USER_BY_ID)) {
            fillPreparedStatement(preparedStatement, user);
            preparedStatement.setLong(5, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> searchUser(String word) {
        String searchWord = "%" + word + "%";
        PreparedStatement preparedStatement = getStatement(SEARCH_USER);
        try {
            preparedStatement.setString(1, searchWord);
            preparedStatement.setString(2, searchWord);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return getListUsers(preparedStatement);
    }

    private PreparedStatement getStatement(String request) {
        Connection connection = new DbConnector().createConnection();
        PreparedStatement preparedStatement = null;
        try {
            preparedStatement = connection.prepareStatement(request);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return preparedStatement;
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        LocalDate dateOfBirth = user.getDateOfBirth();
        Date sqlDateOfBirth = Date.valueOf(dateOfBirth);
        preparedStatement.setDate(3, sqlDateOfBirth);
        preparedStatement.setDouble(4, user.getSalary());
    }

    private List<User> getListUsers(PreparedStatement preparedStatement) {
        List<User> users = new ArrayList<>();
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
}