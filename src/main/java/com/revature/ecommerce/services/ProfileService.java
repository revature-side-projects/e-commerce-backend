package com.revature.ecommerce.services;


import java.util.List;
import java.util.Optional;



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


    public Optional<User> getProfileByEmail(String email) {
		return userRepository.getProfileByEmail(email);
        
    }

    public User editProfile(User user) {
        return userRepository.save(user);
    }
    
   
}