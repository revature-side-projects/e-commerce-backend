package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByCredentials(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
