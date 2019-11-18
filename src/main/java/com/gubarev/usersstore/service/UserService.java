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

    public void deleteUser(long id){
        jdbcUserDao.deleteUser(id);
    }

    public User getUserById(long id){
        return jdbcUserDao.getUserById(id);
    }

    public void editUser(User user){
        jdbcUserDao.editUser(user);
    }

    public List<User> searchUsers(String word){
        return jdbcUserDao.searchUser(word);
    }

}
