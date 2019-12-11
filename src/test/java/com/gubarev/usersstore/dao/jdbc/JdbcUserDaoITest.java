package com.gubarev.usersstore.dao.jdbc;

import com.gubarev.usersstore.PropertyReader;
import com.gubarev.usersstore.dao.UserDao;
import com.gubarev.usersstore.entity.User;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;
import java.util.Properties;

import static org.junit.Assert.*;

public class JdbcUserDaoITest {

    @Test
    public void testGetAll(){
        PropertyReader propertyReader = new PropertyReader();
        Properties properties = propertyReader.getProperties();

        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        DataSource dataSource = dataSourceFactory.getDataSource(properties);

        UserDao userDao = new JdbcUserDao(dataSource);

        List<User> users = userDao.getAll();

        assertFalse(users.isEmpty());
        for (User user: users) {
            assertNotEquals(user.getId(), 0);
            assertNotNull(user.getFirstName());
            assertNotNull(user.getLastName());
            assertNotNull(user.getDateOfBirth());
            assertNotEquals(user.getSalary(), 0);
        }

    }
}
