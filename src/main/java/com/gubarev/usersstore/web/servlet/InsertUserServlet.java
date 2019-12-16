package com.gubarev.usersstore.web.servlet;

import com.gubarev.usersstore.entity.User;
import com.gubarev.usersstore.service.UserService;
import com.gubarev.usersstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class InsertUserServlet extends HttpServlet {
    private PageGenerator pageGenerator = PageGenerator.instance();
    private UserService userService;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        response.getWriter().println(pageGenerator.getPage("add.html"));
    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
        double salary = Double.parseDouble(request.getParameter("salary"));

        User user = new User();
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));
        user.setDateOfBirth(dateOfBirth);
        user.setSalary(salary);

        userService.insertUser(user);
        response.setContentType("text/html;charset=utf-8");
        response.sendRedirect("showAllUsers.html");
    }

    public void setUserService(UserService userService){
        this.userService = userService;
    }


}
