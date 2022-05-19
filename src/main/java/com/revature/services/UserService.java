package com.revature.services;

import com.revature.models.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface UserService {
    Optional<User> findByCredentials(String email, String password);

    User save(User user);

    Boolean findByEmail(String email);

    void sendEmail(User user) throws MessagingException, UnsupportedEncodingException;
}
