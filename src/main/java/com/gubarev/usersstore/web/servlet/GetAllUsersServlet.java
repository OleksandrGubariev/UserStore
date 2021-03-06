package com.gubarev.usersstore.web.servlet;

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
    private PageGenerator pageGenerator = PageGenerator.instance();
    private UserService userService;

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        List<User> users = userService.getAll();
        Map<String, Object> mapUsers = new HashMap<>();
        mapUsers.put("users", users);
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().println(pageGenerator.getPage("showAllUsers.html", mapUsers));
    }

    public void setUsersService(UserService usersService) {
        this.userService = usersService;
    }
}