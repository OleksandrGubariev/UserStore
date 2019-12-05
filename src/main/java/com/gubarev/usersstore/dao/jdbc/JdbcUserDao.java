package com.gubarev.usersstore.dao.jdbc;

import com.gubarev.usersstore.dao.jdbc.mapper.UserRowMapper;
import com.gubarev.usersstore.entities.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {
    private static final String GET_ALL_USERS = "SELECT id, first_name, last_name, date_of_birth, salary FROM Users ORDER BY id ASC;";
    private static final String INSERT_USER = "INSERT INTO Users (first_name, last_name, date_of_birth, salary) VALUES (?, ?, ?, ?);";
    private static final String DELETE_USER = "DELETE FROM Users WHERE id=?;";
    private static final String GET_USER_BY_ID = "SELECT id, first_name, last_name, date_of_birth, salary FROM Users WHERE id=?;";
    private static final String UPDATE_USER_BY_ID = "UPDATE Users SET first_name=?, last_name=?, date_of_birth=?, salary=? WHERE id=?;";
    private static final String SEARCH_USER = "SELECT id, first_name, last_name, date_of_birth, salary FROM Users WHERE LOWER(first_name) LIKE LOWER(?) OR LOWER(last_name) LIKE LOWER(?) ORDER BY id ASC;";
    private DataSource dbConnector;

    public JdbcUserDao(DataSource dbConnector) {
        this.dbConnector = dbConnector;
    }

    public List<User> getAll() {
        try (PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(GET_ALL_USERS)) {
            return getListUsers(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException("Users is'nt founded", e);
        }
    }

    public void insert(User user) {
        try (PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(INSERT_USER)) {
            fillPreparedStatement(preparedStatement, user);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("User is'nt added", e);
        }
    }

    public void delete(long id) {
        try (PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(DELETE_USER)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("User is'nt deleted", e);
        }
    }

    public User getById(long id) {
        try (PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(GET_USER_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            UserRowMapper userRowMapper = new UserRowMapper();
            if (!resultSet.next()) {
                throw new IllegalArgumentException("User does'nt exist");
            }
            return userRowMapper.mapRow(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("User is'nt founded", e);
        }

    }

    public void edit(User user) {
        try (PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(UPDATE_USER_BY_ID)) {
            fillPreparedStatement(preparedStatement, user);
            preparedStatement.setLong(5, user.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("User is'nt edited", e);
        }
    }

    public List<User> search(String word) {
        String searchWord = "%" + word + "%";
        try (PreparedStatement preparedStatement = dbConnector.getConnection().prepareStatement(SEARCH_USER)) {
            preparedStatement.setString(1, searchWord);
            preparedStatement.setString(2, searchWord);
            return getListUsers(preparedStatement);
        } catch (SQLException e) {
            throw new RuntimeException("User is'nt founded", e);
        }
    }

    private void fillPreparedStatement(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getFirstName());
        preparedStatement.setString(2, user.getLastName());
        LocalDate dateOfBirth = user.getDateOfBirth();
        Date sqlDateOfBirth = Date.valueOf(dateOfBirth);
        preparedStatement.setDate(3, sqlDateOfBirth);
        preparedStatement.setDouble(4, user.getSalary());
    }

    private List<User> getListUsers(PreparedStatement preparedStatement) throws SQLException {
        List<User> users = new ArrayList<>();
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            UserRowMapper userRowMapper = new UserRowMapper();
            while (resultSet.next()) {
                User user = userRowMapper.mapRow(resultSet);
                users.add(user);
            }
            return users;
        }
    }
}