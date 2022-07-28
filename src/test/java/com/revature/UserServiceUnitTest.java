package com.revature;


import com.revature.models.User;
import com.revature.models.UserRoles;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UserServiceUnitTest {
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setup() {
        userRepository = Mockito.mock(UserRepository.class);
        userService = new UserService(userRepository);

    }


    User user = new User(1,"email" , "password" , "firstName", "lastName", UserRoles.USER);
    @Test
    public void createUser(){
        Mockito.when(userRepository.save(user)).thenReturn(user);
        User test = userService.createUser(user);
        Assertions.assertEquals(test,user);


    }



}
