package com.gubarev.usersstore.dao.jdbc;

import com.gubarev.usersstore.dao.UserDao;
import com.gubarev.usersstore.dao.jdbc.mapper.UserRowMapper;
import com.gubarev.usersstore.entity.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao implements UserDao {
    private static final String GET_ALL_USERS = "SELECT id, first_name, last_name, date_of_birth, salary FROM Users ORDER BY id ASC;";
    private static final String INSERT_USER = "INSERT INTO Users (first_name, last_name, date_of_birth, salary) VALUES ";
    private static final String DELETE_USER = "DELETE FROM Users WHERE id=";
    private static final String GET_USER_BY_ID = "SELECT id, first_name, last_name, date_of_birth, salary FROM Users WHERE id=";
    private static final String UPDATE_USER_BY_ID = "UPDATE Users SET ";
    private static final String SEARCH_USER = "SELECT id, first_name, last_name, date_of_birth, salary FROM Users WHERE LOWER(first_name) LIKE LOWER(?) OR LOWER(last_name) LIKE LOWER(?) ORDER BY id ASC;";
    private UserRowMapper userRowMapper = new UserRowMapper();
    private DataSource dataSource;

    public JdbcUserDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<User> getAll() {
        try (Statement statement = dataSource.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(GET_ALL_USERS)) {
            return getListUsers(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("Users is'nt founded", e);
        }
    }

    public void insert(User user) {
        StringBuilder queryStringBuilder = new StringBuilder(INSERT_USER);
        queryStringBuilder.append("('");
        queryStringBuilder.append(user.getFirstName());
        queryStringBuilder.append("', '");
        queryStringBuilder.append(user.getLastName());
        queryStringBuilder.append("', '");
        LocalDate dateOfBirth = user.getDateOfBirth();
        Date sqlDateOfBirth = Date.valueOf(dateOfBirth);
        queryStringBuilder.append(sqlDateOfBirth);
        queryStringBuilder.append("', ");
        queryStringBuilder.append(user.getSalary());
        queryStringBuilder.append(");");
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.executeUpdate(queryStringBuilder.toString());
        } catch (SQLException e) {
            throw new RuntimeException("User is'nt added", e);
        }
    }

    public void delete(long id) {
        try (Statement statement = dataSource.getConnection().createStatement()) {
            StringBuilder queryStringBuilder = new StringBuilder(DELETE_USER);
            queryStringBuilder.append(id);
            queryStringBuilder.append(";");
            statement.executeUpdate(queryStringBuilder.toString());
        } catch (SQLException e) {
            throw new RuntimeException("User is'nt deleted", e);
        }
    }

    public User getById(long id) {
        StringBuilder queryStringBuilder = new StringBuilder(GET_USER_BY_ID);
        queryStringBuilder.append(id);
        queryStringBuilder.append(";");
        try (Statement statement = dataSource.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(queryStringBuilder.toString())) {
            if (!resultSet.next()) {
                throw new IllegalArgumentException("User does'nt exist");
            }
            return userRowMapper.mapRow(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("User is'nt founded", e);
        }
    }

    public void edit(User user) {
        StringBuilder queryStringBuilder = new StringBuilder(UPDATE_USER_BY_ID);
        queryStringBuilder.append("first_name='");
        queryStringBuilder.append(user.getFirstName());
        queryStringBuilder.append("', ");
        queryStringBuilder.append("last_name='");
        queryStringBuilder.append(user.getLastName());
        queryStringBuilder.append("', ");
        queryStringBuilder.append("date_of_birth='");
        LocalDate dateOfBirth = user.getDateOfBirth();
        Date sqlDateOfBirth = Date.valueOf(dateOfBirth);
        queryStringBuilder.append(sqlDateOfBirth);
        queryStringBuilder.append("', ");
        queryStringBuilder.append("salary=");
        queryStringBuilder.append(user.getSalary());
        queryStringBuilder.append(" WHERE id=");
        queryStringBuilder.append(user.getId());
        try (Statement statement = dataSource.getConnection().createStatement()) {
            statement.executeUpdate(queryStringBuilder.toString());
        } catch (SQLException e) {
            throw new RuntimeException("User is'nt edited", e);
        }
    }

    public List<User> search(String word) {
        String searchWord = "'%" + word + "%'";
        String searchUser = SEARCH_USER.replace("?", searchWord);
        try (Statement statement = dataSource.getConnection().createStatement();
             ResultSet resultSet = statement.executeQuery(searchUser)) {
            return getListUsers(resultSet);
        } catch (SQLException e) {
            throw new RuntimeException("User is'nt founded", e);
        }
    }

    private List<User> getListUsers(ResultSet resultSet) throws SQLException {
        List<User> users = new ArrayList<>();
        while (resultSet.next()) {
            User user = userRowMapper.mapRow(resultSet);
            users.add(user);
        }
        return users;
    }
}