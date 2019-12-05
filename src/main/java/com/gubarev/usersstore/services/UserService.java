package com.gubarev.usersstore.services;

import com.gubarev.usersstore.dao.jdbc.UserDao;
import com.gubarev.usersstore.entities.User;

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

    public void deleteUser(long id){
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
