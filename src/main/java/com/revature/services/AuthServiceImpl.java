package com.revature.services;

import com.revature.models.User;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserServiceImpl userService;

    public AuthServiceImpl(UserServiceImpl userService) {
        this.userService = userService;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userService.findByCredentials(email, password);
    }

    public User register(User user) {
        return userService.save(user);
    }

    @Override
    public boolean forgotPassword(String email){
        if(userService.findByEmail(email)) {
            System.out.println("email sent");
            //TODO POST to reset reset request table {uuid,timestamp,userId}
            userService.sendEmail(email);
            //TODO alert that an email has been sent to the address with the reset password list  -- FRONT END (RYAN)
            return true;
        }
        //TODO alert that an email has been sent to the address with the reset password list -- FRONT END (RYAN)
        return false;
    }
}
