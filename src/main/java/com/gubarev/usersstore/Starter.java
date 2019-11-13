package com.gubarev.usersstore;

import com.gubarev.usersstore.dao.jdbc.JdbcUserDao;
import com.gubarev.usersstore.service.UserService;
import com.gubarev.usersstore.web.servlets.GetAllUsersServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class Starter {
    public static void main(String[] args) throws Exception {
        JdbcUserDao jdbcUserDao = new JdbcUserDao();

        //config services
        UserService userService = new UserService();
        userService.setJdbcUserDao(jdbcUserDao);

        //config servlets
        GetAllUsersServlet getAllUsersServlet = new GetAllUsersServlet();
        getAllUsersServlet.setUsersService(userService);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(getAllUsersServlet), "/show");

        //start server
        Server server = new Server(8080);
        server.setHandler(context);
        server.start();


    }
}