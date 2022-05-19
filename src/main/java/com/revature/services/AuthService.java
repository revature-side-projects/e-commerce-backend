package com.revature.services;

import com.revature.models.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface AuthService {
    Optional<User> findByCredentials(String email, String password);

    User register(User user);

    /**
     * A method that will determine if the user is registered and then email the account on file.
     * @param email the email to search for in the user table of the database
     * @return true - if the email was found in the database (not intended to be passed to front end)
     */
    boolean forgotPassword(String email);
}
