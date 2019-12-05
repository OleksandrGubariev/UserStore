package com.gubarev.usersstore.web.servlets;

import com.gubarev.usersstore.entities.User;
import com.gubarev.usersstore.services.UserService;
import com.gubarev.usersstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

public class InsertUserServlet extends HttpServlet {
    private UserService usersService;
    private PageGenerator pageGenerator = PageGenerator.instance();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {

        response.getWriter().println(pageGenerator.getPage("add.html"));

    }

    public void doPost(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        User user = new User();
        user.setFirstName(request.getParameter("firstName"));
        user.setLastName(request.getParameter("lastName"));

        LocalDate dateOfBirth = LocalDate.parse(request.getParameter("dateOfBirth"));
        user.setDateOfBirth(dateOfBirth);

        double salary = Double.parseDouble(request.getParameter("salary"));
        user.setSalary(salary);

        usersService.insertUser(user);

        response.getWriter().println(pageGenerator.getPage("insertSuccess.html", null));
    }

    public void setUsersService(UserService usersService){
        this.usersService = usersService;
    }


}
