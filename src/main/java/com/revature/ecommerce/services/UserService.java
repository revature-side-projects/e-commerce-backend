package com.revature.ecommerce.services;

import com.revature.ecommerce.models.User;
import com.revature.ecommerce.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
