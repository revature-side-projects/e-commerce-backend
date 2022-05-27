package com.revature;


import com.revature.controllers.AuthController;
import com.revature.dtos.ResetRequestEmail;
import com.revature.models.ResetRequest;
import com.revature.models.User;
import com.revature.repositories.ResetRequestRepository;
import com.revature.repositories.UserRepository;
import com.revature.services.AuthService;
import com.revature.services.ResetService;
import com.revature.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResetRequestRegistationTests {

    //TEST DEPENDENCIES

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthController authController;

    @Autowired
    private ResetRequestRepository passwordRepository;

    @Autowired
    private ResetService resetService;

    @Test
    void findEmailRepositoryTest(){
        Assertions.assertTrue(userRepository.findByEmail("testuser@gmail.com").isPresent());
    }

    @Test
    void findEmailServiceTest(){
        Assertions.assertTrue(userService.findByEmail("testuser@gmail.com").isPresent());
    }

    @Test
    void findEmailAuthorizeTest(){
       // Assertions.assertTrue(authService.forgotPassword("testuser@gmail.com"));
    }

    @Test
    void resetRequestControllerTest(){
        ResetRequestEmail request = new ResetRequestEmail("testuser@gmail.com");
        //Assertions.assertTrue(authController.passwordResetRequest(request));
    }
    @Test void sendEmailTest() {
        userService.sendEmail("testuser@gmail.com",1);
    }

    @Test void findResetPasswordTest(){
        Assertions.assertNotNull(resetService.findById(1));
    }

    @Test void resetPassword(){
        User user =resetService.reset("resetTest",new ResetRequest(1,1234567,1));
        Assertions.assertEquals("resetTest", user.getPassword());
    }

}
