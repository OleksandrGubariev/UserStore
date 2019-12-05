package com.gubarev.usersstore.dao.jdbc;

import com.gubarev.usersstore.entities.User;

import java.util.List;

public interface UserDao {
    List<User> getAll();
    void delete(long id);
    void insert(User user);
    void edit(User user);
    List<User> search(String word);
    User getById(long id);
}
