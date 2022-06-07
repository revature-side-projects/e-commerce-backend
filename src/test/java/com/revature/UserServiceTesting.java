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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
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
        Optional<User> u = Optional.of(new User(0,"a","b","c","d"));
        when((ur).findByEmailAndPassword(any(),any())).thenReturn(u);
        Optional<User> test = us.findByCredentials("a","b");
        verify(ur).findByEmailAndPassword(any(), any());
        assertEquals(0,test.get().getId(), "pass");
        assertEquals("a",test.get().getEmail(), "pass");
        assertEquals("b",test.get().getPassword(), "pass");
        assertEquals("c",test.get().getFirstName(), "pass");
        assertEquals("d",test.get().getLastName(), "pass");
    }

    @Test
    public void saveTest(){
        User u = new User(0,"a","b","c","d");
        when((ur).save(any())).thenReturn(u);
        User test = us.save(u);
        verify(ur).save(any());
        assertEquals(0,test.getId(), "pass");
        assertEquals("a",test.getEmail(), "pass");
        assertEquals("b",test.getPassword(), "pass");
        assertEquals("c",test.getFirstName(), "pass");
        assertEquals("d",test.getLastName(), "pass");
    }

}
