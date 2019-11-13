package com.gubarev.usersstore.dao.jdbc;

import com.gubarev.usersstore.dao.jdbc.mapper.RowsMapper;
import com.gubarev.usersstore.entity.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JdbcUserDao {
    private static final String GET_ALL_USERS = "SELECT id, first_name, last_name, date_of_birth, salary FROM User;";
   //private static final String INSERT_USER = "INSERT User (first_name, last_name, date_of_birth, salary) VALUES (?, ?, ?, ?);";

    public List<User> getGetAllUsers()  {
        DbConnector dbConnector = new DbConnector();
        Connection connection = dbConnector.createConnection();
        List<User> users = new ArrayList<>();
        try(Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(GET_ALL_USERS)){
            RowsMapper rowsMapper = new RowsMapper();
            while (resultSet.next()) {
                User user = rowsMapper.userRowMapper(resultSet);
                users.add(user);
            }
        }
        catch (SQLException e){
            try {
                throw new SQLException("Database access error");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return users;
    }
}
