package com.revature.services;

import com.revature.models.User;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    public Optional<User> findByCredentials(String username, String password) {
        return userService.findByCredentials(username, password);
    }

    public User register(User user) {
        return userService.save(user);
    }
}
