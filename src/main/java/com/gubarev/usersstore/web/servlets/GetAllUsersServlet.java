package com.gubarev.usersstore.web.servlets;

import com.gubarev.usersstore.entity.User;
import com.gubarev.usersstore.service.UserService;
import com.gubarev.usersstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GetAllUsersServlet extends HttpServlet {

    private UserService userService;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        List<User> users = userService.getAll();
        Map<String, Object> mapUsers = new HashMap<>();
        mapUsers.put("users", users);
        response.getWriter().println(PageGenerator.instance().getPage("showAllUsers.html", mapUsers));
    }

    public void setUsersService(UserService usersService) {
        this.userService = usersService;
    }


//    private void addUser(HttpServletRequest request,
//                         HttpServletResponse response) throws ParseException {
//        String firstName = request.getParameter("firstName");
//        String lastName = request.getParameter("lastName");
//
//        java.util.Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateOfBirth"));
//        java.sql.Date sqlDateOfBirth = new java.sql.Date(dateOfBirth.getTime());
//
//        double salary = Double.parseDouble(request.getParameter("salary"));
//
//        try (Connection connection = new DbConnector().createConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER)) {
//            preparedStatement.setString(1, firstName);
//            preparedStatement.setString(2, lastName);
//            preparedStatement.setDate(3, sqlDateOfBirth);
//            preparedStatement.setDouble(4, salary);
//            preparedStatement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}