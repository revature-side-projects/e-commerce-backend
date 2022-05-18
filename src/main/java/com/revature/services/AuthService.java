package com.revature.services;

import com.revature.models.User;

import java.util.Optional;

public interface AuthService {
    public Optional<User> findByCredentials(String email, String password);

    public User register(User user);
}
