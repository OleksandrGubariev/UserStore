package com.gubarev.usersstore.web.servlet;

import com.gubarev.usersstore.entity.User;
import com.gubarev.usersstore.service.UserService;
import com.gubarev.usersstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EditUserServlet extends HttpServlet {
    private PageGenerator pageGenerator = PageGenerator.instance();
    private UserService userService;

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        long userId = Long.parseLong(request.getParameter("id"));
        User user = userService.getUserById(userId);
        Map<String, Object> mapUsers = new HashMap<>();
        mapUsers.put("user", user);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(pageGenerator.getPage("edit.html", mapUsers));
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User();
        long userId = Long.parseLong(request.getParameter("id"));
        double salary = Double.parseDouble(request.getParameter("salary"));

        user.setId(userId);
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
        user.setDateOfBirth(dateOfBirth);
        user.setSalary(salary);

        userService.editUser(user);
        response.setContentType("text/html; charset=utf-8");
        response.sendRedirect("showAllUsers.html");
    }


    public void setUserService(UserService userService) {
        this.userService = userService;
    }

}
