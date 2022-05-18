package com.revature.services;

import com.revature.models.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> findByCredentials(String email, String password);

    public User save(User user);
}
