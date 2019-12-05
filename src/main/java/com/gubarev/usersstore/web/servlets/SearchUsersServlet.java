package com.gubarev.usersstore.web.servlets;

import com.gubarev.usersstore.entities.User;
import com.gubarev.usersstore.services.UserService;
import com.gubarev.usersstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchUsersServlet extends HttpServlet {
    private UserService usersService;
    private PageGenerator pageGenerator = PageGenerator.instance();

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws IOException {
        String searchWord = request.getParameter("searchWord");

        List<User> users = usersService.searchUsers(searchWord);
        Map<String, Object> mapUsers = new HashMap<>();
        mapUsers.put("users", users);
        response.getWriter().println(pageGenerator.getPage("showAllUsers.html", mapUsers));

    }

    public void setUsersService(UserService usersService) {
        this.usersService = usersService;
    }
}
