package com.gubarev.usersstore;

import com.gubarev.usersstore.dao.jdbc.DbConnector;
import com.gubarev.usersstore.dao.jdbc.JdbcUserDao;
import com.gubarev.usersstore.dao.jdbc.UserDao;
import com.gubarev.usersstore.services.UserService;
import com.gubarev.usersstore.web.servlets.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.sql.DataSource;

public class Starter {
    public static void main(String[] args) throws Exception {
        DataSource dbConnector = new DbConnector();
        UserDao jdbcUserDao = new JdbcUserDao(dbConnector);

        //config services
        UserService userService = new UserService();
        userService.setUserDao(jdbcUserDao);

        //config servlets
        GetAllUsersServlet getAllUsersServlet = new GetAllUsersServlet();
        getAllUsersServlet.setUsersService(userService);

        InsertUserServlet insertUserServlet = new InsertUserServlet();
        insertUserServlet.setUsersService(userService);

        DeleteUserServlet deleteUserServlet = new DeleteUserServlet();
        deleteUserServlet.setUsersService(userService);

        EditUserServlet editUserServlet = new EditUserServlet();
        editUserServlet.setUsersService(userService);

        SearchUsersServlet searchUsersServlet = new SearchUsersServlet();
        searchUsersServlet.setUsersService(userService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(getAllUsersServlet), "/");
        context.addServlet(new ServletHolder(insertUserServlet), "/add");
        context.addServlet(new ServletHolder(deleteUserServlet), "/delete");
        context.addServlet(new ServletHolder(editUserServlet), "/edit");
        context.addServlet(new ServletHolder(searchUsersServlet), "/search");

        //start server
        String serverPortEnv = System.getenv("PORT");
        int serverPort = serverPortEnv != null ? Integer.parseInt(serverPortEnv) : 8080;

        Server server = new Server(serverPort);
        server.setHandler(context);
        server.start();


    }
}