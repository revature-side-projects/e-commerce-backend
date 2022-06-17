package com.revature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.AuthController;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.User;
import com.revature.repositories.UserRepository;
import com.revature.services.AuthService;
import com.revature.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.LinkedHashMap;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ECommerceApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository ur;

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
                .andExpect(jsonPath("$.password").value("")).andExpect(jsonPath("$.firstName").value("a"))
                .andExpect(jsonPath("$.lastName").value("b")).andExpect(jsonPath("$.admin").value(false));
    }

    @Test
    @Transactional
    public void failedLoginTest()throws Exception{
        LoginRequest loginRequest = new LoginRequest("testuser@gmail.com","word");
        User u = new User(0,"testuser@gmail.com","password","a","b",false);
        ur.save(u);
        mockMvc.perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(loginRequest))
        ).andDo(print()).andExpect(status().isBadRequest());
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
                .andExpect(jsonPath("$.password").value("")).andExpect(jsonPath("$.firstName").value("a"))
                .andExpect(jsonPath("$.lastName").value("b")).andExpect(jsonPath("$.admin").value(false));
    }

    @Test
    @Transactional
    public void failedSaveUserTest()throws Exception{
        RegisterRequest registerRequest = new RegisterRequest("test","pass","a","b",false);
        //HttpSession session = new MockHttpSession();
        User u = new User(0,"test","pass","a","b",false);
        ur.save(u);
        mockMvc.perform(post("/auth/register").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(registerRequest))
        ).andDo(print()).andExpect(status().isConflict());
    }

//    @Test
//    @Transactional
//    public void checkGuestTest()throws Exception{
//        //RegisterRequest registerRequest = new RegisterRequest("test","pass","a","b",false);
//        //HttpSession session = new MockHttpSession();
//        User u = new User();
//        // (0,"test","pass","a","b",false);
//        //ur.save(u);
//        //User u = null;
//
//        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
//        sessionAttr.put("user", u);
//        MvcResult content = mockMvc.perform(post("/auth/checkLogin").sessionAttrs(sessionAttr)).andDo(print()).andExpect(status().isOk()).andReturn();
//        String result = content.getResponse().getContentAsString();
//        assertEquals("1",result);
//    }

//    @Test
//    @Transactional
//    public void checkUserTest()throws Exception{
//
//        //HttpSession session = new MockHttpSession();
//        //User u = new User(0,"test","pass","a","b",false);
//        //ur.save(u);
//        User u = new User(0,"test","pass","a","b",false);
//
//        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
//        sessionAttr.put("user", u);
//        MvcResult content = mockMvc.perform(post("/auth/checkLogin").sessionAttrs(sessionAttr)).andDo(print()).andExpect(status().isOk()).andReturn();
//        String result = content.getResponse().getContentAsString();
//        assertEquals("2",result);
//    }

//    @Test
//    @Transactional
//    public void checkAdminTest()throws Exception{
//
//        User u = new User(0,"test","pass","a","b",true);
//
//        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
//        sessionAttr.put("user", u);
//        MvcResult content = mockMvc.perform(post("/auth/checkLogin").sessionAttrs(sessionAttr)).andDo(print()).andExpect(status().isOk()).andReturn();
//        String result = content.getResponse().getContentAsString();
//        assertEquals("3",result);
//    }
}

