package com.gubarev.usersstore.web.servlets;

import com.gubarev.usersstore.entities.User;
import com.gubarev.usersstore.services.UserService;
import com.gubarev.usersstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class EditUserServlet extends HttpServlet {
    private UserService usersService;
    private PageGenerator pageGenerator = PageGenerator.instance();

    public void doGet(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        long userId = Long.parseLong(request.getParameter("id"));
        User user = usersService.getUserById(userId);
        Map<String, Object> mapUsers = new HashMap<>();
        mapUsers.put("user", user);
        response.getWriter().println(pageGenerator.getPage("edit.html", mapUsers));

    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = new User();
        long userId = Long.parseLong(request.getParameter("id"));
        user.setId(userId);
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));

        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
        user.setDateOfBirth(dateOfBirth);

        double salary = Double.parseDouble(request.getParameter("salary"));
        user.setSalary(salary);

        usersService.editUser(user);
        response.getWriter().println(pageGenerator.getPage("editSuccess.html", null));
    }


    public void setUsersService(UserService usersService) {
        this.usersService = usersService;
    }

}
