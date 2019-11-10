package com.gubarev.usersstore.servlets;

import com.gubarev.usersstore.db.DbConnector;
import com.gubarev.usersstore.entity.User;
import com.gubarev.usersstore.templater.PageGenerator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class AllRequestsServlet extends HttpServlet {
    private static final String SELECT_ALL_USERS = "SELECT * FROM User";
    private static final String INSERT_USER = "INSERT User (first_name, last_name, date_of_birth, salary) VALUES (?, ?, ?, ?);";

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException{
        String action = request.getPathInfo();
        if (action.equalsIgnoreCase("/add")) {
            response.getWriter().println(PageGenerator.instance().getPage("add.html", null));
        } else if (action.equals("/page")) {
            List<User> allUsers = selectAllUsers();
            if (allUsers.size() == 0) {
                response.getWriter().println("Users not found");
            } else {
                Map<String, Object> users = new HashMap<>();
                users.put("users", allUsers);
                response.getWriter().println(PageGenerator.instance().getPage("page.html", users));
            }
        } else if (action.equals("/insert")) {
            try {
                addUser(request, response);
                response.sendRedirect("/page");
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    private List<User> selectAllUsers() {
        List<User> allUsers = new ArrayList<>();

        try (Connection connection = new DbConnector().createConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_USERS)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                Date dateOfBirth = resultSet.getDate("date_of_birth");
                double salary = resultSet.getDouble("salary");
                allUsers.add(new User(id, firstName, lastName, salary, dateOfBirth));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return allUsers;
    }

    private void addUser(HttpServletRequest request,
                         HttpServletResponse response) throws ParseException {
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        java.util.Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateOfBirth"));
        java.sql.Date sqlDateOfBirth =  new java.sql.Date(dateOfBirth.getTime());

        double salary =  Double.parseDouble(request.getParameter("salary"));

        try (Connection connection = new DbConnector().createConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);
            preparedStatement.setDate(3, sqlDateOfBirth);
            preparedStatement.setDouble(4, salary);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}