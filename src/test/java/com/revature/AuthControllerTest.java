package com.revature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.AuthService;
import com.revature.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.LinkedHashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ECommerceApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository ur;

//    @Autowired
//    private UserService us;
//
//    @Autowired
//    static AuthService as;

    @BeforeEach
    public void resetDB() {
        ur.deleteAll();
    }

    private ObjectMapper om = new ObjectMapper();

    @Test
    @Transactional
    public void loginTest()throws Exception{
        LoginRequest loginRequest = new LoginRequest("testuser@gmail.com","password");
        //HttpSession session = new MockHttpSession();
        User u = new User(0,"testuser@gmail.com","password","a","b",false);
        ur.save(u);
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(loginRequest))
                ).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.email").value("testuser@gmail.com"))
                .andExpect(jsonPath("$.password").value("password")).andExpect(jsonPath("$.firstName").value("a"))
                .andExpect(jsonPath("$.lastName").value("b")).andExpect(jsonPath("$.admin").value(false)).andExpect(jsonPath("$.id").value(3));
    }

        @Test
        @Transactional
        public void saveUserTest()throws Exception{
        RegisterRequest registerRequest = new RegisterRequest("test","pass","a","b",false);
        //HttpSession session = new MockHttpSession();
        //User u = new User(0,"test","pass","a","b",false);
        //ur.saveAndFlush(u);
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(registerRequest))
                ).andDo(print()).andExpect(status().isCreated()).andExpect(jsonPath("$.email").value("test"))
                .andExpect(jsonPath("$.password").value("pass")).andExpect(jsonPath("$.firstName").value("a"))
                .andExpect(jsonPath("$.lastName").value("b")).andExpect(jsonPath("$.admin").value(false)).andExpect(jsonPath("$.id").value(3));
    }

}
