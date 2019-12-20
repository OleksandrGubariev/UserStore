package com.gubarev.usersstore.web.servlet;

import com.gubarev.usersstore.service.UserService;
import com.gubarev.usersstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserServlet extends HttpServlet {
    private UserService userService;

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        long userId = Long.parseLong(request.getParameter("id"));

        userService.deleteUserById(userId);
        response.setContentType("text/html; charset=utf-8");
        response.sendRedirect("showAllUsers.html");
    }

    public void setUserService(UserService userService){
        this.userService = userService;
    }
}
