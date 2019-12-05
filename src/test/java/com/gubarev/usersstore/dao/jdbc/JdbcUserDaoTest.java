package com.gubarev.usersstore.dao.jdbc;

import com.gubarev.usersstore.entities.User;
import org.junit.Test;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.Assert.*;

public class JdbcUserDaoTest {

    @Test
    public void testGetAll(){
        DataSource dbConnector = new DbConnector();
        UserDao jdbcUserDao = new JdbcUserDao(dbConnector);

        List<User> users = jdbcUserDao.getAll();

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
