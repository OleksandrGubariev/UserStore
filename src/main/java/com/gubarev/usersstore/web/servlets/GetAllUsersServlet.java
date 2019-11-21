package com.gubarev.usersstore.web.servlets;

import com.gubarev.usersstore.entity.User;
import com.gubarev.usersstore.service.UserService;
import com.gubarev.usersstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
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
}