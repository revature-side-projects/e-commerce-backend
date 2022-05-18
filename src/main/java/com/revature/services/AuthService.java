package com.revature.services;

import com.revature.models.User;

import java.util.Optional;

public interface AuthService {
    Optional<User> findByCredentials(String email, String password);

    User register(User user);

    boolean forgotPassword(String email);
}
