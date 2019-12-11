package com.gubarev.usersstore.service;

import com.gubarev.usersstore.dao.UserDao;
import com.gubarev.usersstore.entity.User;

import java.util.List;

public class UserService {
    private UserDao userDao;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public List<User> getAll() {
        return userDao.getAll();
    }

    public void insertUser(User user){
        userDao.insert(user);
    }

    public void deleteUserById(long id){
        userDao.delete(id);
    }

    public User getUserById(long id){
        return userDao.getById(id);
    }

    public void editUser(User user){
        userDao.edit(user);
    }

    public List<User> searchUsers(String word){
        return userDao.search(word);
    }

}
