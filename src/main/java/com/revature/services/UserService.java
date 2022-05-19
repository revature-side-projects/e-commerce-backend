package com.revature.services;

import com.revature.models.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface UserService {
    Optional<User> findByCredentials(String email, String password);

    User save(User user);

    /**
     * A method to find if the email is registered in the user table of the database
     * @param email the email to search for
     * @return true - if the email is found in the database
     */
    boolean findByEmail(String email);

    /**
     * A method to send an email for password resets
     * @param email the email that the password reset confirmation will be sent to
     */
    void sendEmail(String email) throws MessagingException, UnsupportedEncodingException;
}
