package com.revature.services;

import com.revature.models.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByCredentials(String email, String password);

    Optional<User> findByEmail(String email);

    User save(User user);
}
