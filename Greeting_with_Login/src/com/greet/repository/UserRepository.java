package com.greet.repository;

import com.greet.model.User;

public interface UserRepository {

    User login(String username, String password);

    boolean register(User user);

    User getUserById(int id);

    User getUserByUsername(String username);
}
