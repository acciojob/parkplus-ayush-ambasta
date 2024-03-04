package com.driver.services.impl;

import com.driver.model.User;
import com.driver.repository.UserRepository;
import com.driver.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository4;
    @Override
    public void deleteUser(Integer userId) {
        Optional<User> optionalUser = userRepository4.findById(userId);
        if(optionalUser.isEmpty())return;
        userRepository4.deleteById(userId);
    }

    @Override
    public User updatePassword(Integer userId, String password) {
        Optional<User> optionalUser = userRepository4.findById(userId);
        if(optionalUser.isEmpty())return null;
        User user = optionalUser.get();
        user.setPassword(password);
        return userRepository4.save(user);
    }

    @Override
    public void register(String name, String phoneNumber, String password) {
        User newUser = new User();
        newUser.setName(name);
        newUser.setPhoneNumber(phoneNumber);
        newUser.setPassword(password);
        userRepository4.save(newUser);
    }
}
