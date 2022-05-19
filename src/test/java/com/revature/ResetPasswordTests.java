package com.revature;


import com.revature.controllers.AuthController;
import com.revature.dtos.ResetRequest;
import com.revature.repositories.UserRepository;
import com.revature.services.AuthService;
import com.revature.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ResetPasswordTests {

    //TEST DEPENDENCIES

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;

    @Autowired
    private AuthController authController;

    @Test
    void findEmailRepositoryTest(){
        Assertions.assertTrue(userRepository.findByEmail("testuser@gmail.com").isPresent());
    }

    @Test
    void findEmailServiceTest(){
        Assertions.assertTrue(userService.findByEmail("testuser@gmail.com"));
    }

    @Test
    void findEmailAuthorizeTest(){
        Assertions.assertTrue(authService.forgotPassword("testuser@gmail.com"));
    }

    @Test
    void resetRequestControllerTest(){
        ResetRequest request = new ResetRequest("testuser@gmail.com");
        Assertions.assertTrue(authController.passwordResetRequest(request));
    }
}
