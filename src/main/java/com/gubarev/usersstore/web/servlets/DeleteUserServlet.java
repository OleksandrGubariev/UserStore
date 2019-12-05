package com.gubarev.usersstore.web.servlets;

import com.gubarev.usersstore.services.UserService;
import com.gubarev.usersstore.web.templater.PageGenerator;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteUserServlet extends HttpServlet {
    private UserService usersService;
    private PageGenerator pageGenerator = PageGenerator.instance();

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {

        long userId = Long.parseLong(request.getParameter("id"));

        usersService.deleteUser(userId);

        response.getWriter().println(pageGenerator.getPage("deleteSuccess.html"));
    }

    public void setUsersService(UserService usersService){
        this.usersService = usersService;
    }
}
