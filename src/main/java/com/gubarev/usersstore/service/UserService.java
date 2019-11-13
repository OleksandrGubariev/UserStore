package com.gubarev.usersstore.service;

import com.gubarev.usersstore.dao.jdbc.JdbcUserDao;
import com.gubarev.usersstore.entity.User;

import java.util.List;

public class UserService {
    private JdbcUserDao jdbcUserDao;

    public void setJdbcUserDao(JdbcUserDao jdbcUserDao) {
        this.jdbcUserDao = jdbcUserDao;
    }

    public List<User> getAll() {
        return jdbcUserDao.getAllUsers();
    }

    public void insertUser(User user){
        jdbcUserDao.insertUser(user);
    }


}
