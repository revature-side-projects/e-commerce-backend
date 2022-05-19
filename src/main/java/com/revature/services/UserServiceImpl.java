package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private JavaMailSender mailSender;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByCredentials(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean findByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public void sendEmail(String email){
        try {
            String subject = "Placeholder subject";
            String senderName = "RevatureMerchTeam";
            String mailContent = "<p>Hello world</p>" + "<b>Hello bolded edition</b>";
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom("isaiahlee667@gmail.com", senderName); //TODO Update with company email @no-reply address
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(mailContent, true);
            mailSender.send(message);
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
