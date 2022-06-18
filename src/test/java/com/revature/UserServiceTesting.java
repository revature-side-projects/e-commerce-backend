package com.revature;
import java.util.Optional;

import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@SpringBootTest
public class UserServiceTesting {


    @BeforeEach
    public void registrationBeforeTest(){
        MockitoAnnotations.openMocks(this);
    }

    @Mock
    static UserRepository ur;

    @InjectMocks
    static UserService us;

    @Test

    public void findByCredentialsTest(){
        UserService uss = new UserService(ur);
        Optional<User> u = Optional.of(new User(0,"a","b","c","d",false));
        when((ur).findByEmailAndPassword(any(),any())).thenReturn(u);
        Optional<User> test = uss.findByCredentials("a","b");
        verify(ur).findByEmailAndPassword(any(), any());
        assertEquals(0,test.get().getId(), "pass");
        assertEquals("a",test.get().getEmail(), "pass");
        assertEquals("b",test.get().getPassword(), "pass");
        assertEquals("c",test.get().getFirstName(), "pass");
        assertEquals("d",test.get().getLastName(), "pass");
        assertEquals(false,test.get().isAdmin(), "pass");
    }
}
