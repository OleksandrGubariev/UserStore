package com.gubarev.usersstore.web.servlets;

import com.gubarev.usersstore.entities.User;
import com.gubarev.usersstore.services.UserService;
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
    private PageGenerator pageGenerator = PageGenerator.instance();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        List<User> users = userService.getAll();
        Map<String, Object> mapUsers = new HashMap<>();
        mapUsers.put("users", users);

        response.getWriter().println(pageGenerator.getPage("showAllUsers.html", mapUsers));
    }

    public void setUsersService(UserService usersService) {
        this.userService = usersService;
    }
}