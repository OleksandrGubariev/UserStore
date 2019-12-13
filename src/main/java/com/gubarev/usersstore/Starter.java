package com.gubarev.usersstore;

import com.gubarev.usersstore.dao.jdbc.DataSourceFactory;
import com.gubarev.usersstore.dao.jdbc.JdbcUserDao;
import com.gubarev.usersstore.dao.UserDao;
import com.gubarev.usersstore.service.UserService;
import com.gubarev.usersstore.web.servlet.*;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.sql.DataSource;
import java.util.Properties;

public class Starter {
    public static void main(String[] args) throws Exception {

        PropertyReader propertyReader = new PropertyReader();
        Properties properties = propertyReader.getProperties();

        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        DataSource dataSource = dataSourceFactory.getDataSource(properties);

        UserDao userDao = new JdbcUserDao(dataSource);

        //config services
        UserService userService = new UserService();
        userService.setUserDao(userDao);

        //config servlets
        GetAllUsersServlet getAllUsersServlet = new GetAllUsersServlet();
        getAllUsersServlet.setUsersService(userService);

        InsertUserServlet insertUserServlet = new InsertUserServlet();
        insertUserServlet.setUserService(userService);

        DeleteUserServlet deleteUserServlet = new DeleteUserServlet();
        deleteUserServlet.setUserService(userService);

        EditUserServlet editUserServlet = new EditUserServlet();
        editUserServlet.setUserService(userService);

        SearchUsersServlet searchUsersServlet = new SearchUsersServlet();
        searchUsersServlet.setUserService(userService);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.addServlet(new ServletHolder(getAllUsersServlet), "/");
        context.addServlet(new ServletHolder(insertUserServlet), "/add");
        context.addServlet(new ServletHolder(deleteUserServlet), "/delete");
        context.addServlet(new ServletHolder(editUserServlet), "/edit");
        context.addServlet(new ServletHolder(searchUsersServlet), "/search");

        //start server
        int serverPort = Integer.parseInt(properties.getProperty("PORT"));
        Server server = new Server(serverPort);
        server.setHandler(context);
        server.start();


    }
}