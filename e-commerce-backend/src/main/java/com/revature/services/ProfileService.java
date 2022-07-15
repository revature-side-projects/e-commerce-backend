package com.revature.services;

import java.util.Optional;


import org.springframework.stereotype.Service;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@Service
public class ProfileService {

    private final UserRepository userRepository;

    public ProfileService(UserRepository userRepository) {
        this.userRepository=userRepository;
    }


    public Optional<User> getProfileById(int id) {
        return userRepository.findById(id);
    }

    public User editProfile(User user) {
        return userRepository.save(user);
    }
}
