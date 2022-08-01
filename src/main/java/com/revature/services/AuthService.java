package com.revature.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.models.User;

/**
 * Performs tasks such as registering new user and finding a User by credentials
 *
 */
@Service
public class AuthService {

    private final UserService userService;

    public AuthService(UserService userService) {
        this.userService = userService;
    }

    /**
     * Attempts to find User given credentials passed
     * @param email
     * @param password
     * @return Optional<User>
     */
    public Optional<User> findByCredentials(String email, String password) {
        return userService.findByCredentials(email, password);
    }

    /**
     * Registers a new User
     * @param user
     * @return User
     */
    public User register(User user) {
        return userService.save(user);
    }
}
