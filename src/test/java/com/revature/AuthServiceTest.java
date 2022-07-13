package com.revature;

import java.util.Optional;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.AuthService;
import com.revature.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
public class AuthServiceTest {

    @BeforeEach
    public void registrationBeforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    static UserRepository ur;

    @Autowired
    static UserService us = new UserService(ur);

    @Autowired
    static AuthService as;

    @Test
    public void authFindByCredentialsTest(){
        us = new UserService(ur);
        as = new AuthService(us);
        Optional<User> u = Optional.of(new User(0,"a","b","c","d", false));
        when((ur).findByEmailAndPassword(any(),any())).thenReturn(u);
        Optional<User> test = as.findByCredentials("a","b");
        verify(ur).findByEmailAndPassword(any(),any());
        assertEquals(0,test.get().getId(), "pass");
        assertEquals("a",test.get().getEmail(), "pass");
        assertEquals("b",test.get().getPassword(), "pass");
        assertEquals("c",test.get().getFirstName(), "pass");
        assertEquals("d",test.get().getLastName(), "pass");
    }

    @Test
    public void registerTest(){
        us = new UserService(ur);
        as = new AuthService(us);
        User u = new User(0,"a","b","c","d", false);
        when((ur).save(any())).thenReturn(u);
        User test = as.register(u);
        verify(ur).save(any());
        assertEquals(0,test.getId(), "pass");
        assertEquals("a",test.getEmail(), "pass");
        assertEquals("b",test.getPassword(), "pass");
        assertEquals("c",test.getFirstName(), "pass");
        assertEquals("d",test.getLastName(), "pass");
    }




}
