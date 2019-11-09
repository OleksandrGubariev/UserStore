package com.gubarev.usersstore.servlets;

import com.gubarev.usersstore.db.DbConnector;
import com.gubarev.usersstore.entity.User;
import com.gubarev.usersstore.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class AllRequestsServlet extends HttpServlet {
    private static final String SELECT_ALL_USERS = "SELECT * FROM User";

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        List<User> allUsers = selectAllUsers();
        Map<String, Object> users = new HashMap<>();
        users.put( "users", allUsers );
        response.getWriter().println(PageGenerator.instance().getPage("page.html", users));

    }

    private List<User> selectAllUsers(){
        List<User> allUsers = new ArrayList<>();
        Connection connection = new DbConnector().createConnection();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS);

            while(resultSet.next()){
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName  = resultSet.getString("last_name");
                Date dateOfBirth = resultSet.getDate("date_of_birth");
                double salary = resultSet.getDouble("salary");
                allUsers.add(new User(id, firstName, lastName, salary, dateOfBirth));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }
}