package com.greet.service;

import com.greet.model.User;
import com.greet.repository.UserRepository;
import com.greet.util.HashUtil;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

//    public void setUserRepository(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }

    @Override
    public User login(String username, String password) {

        String hashedPassword = HashUtil.hashPassword(password);

        System.out.println("Username: " + username);
        System.out.println("Original Password: " + password);
        System.out.println("Hashed Password: " + hashedPassword);

        return userRepository.login(username, hashedPassword);
    }

    @Override
    public boolean register(User user) {

        user.setPassword(HashUtil.hashPassword(user.getPassword()));

        return userRepository.register(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.getUserByUsername(username);
    }
}
