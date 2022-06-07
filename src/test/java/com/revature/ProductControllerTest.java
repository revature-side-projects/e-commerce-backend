package com.revature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.Product;
import com.revature.models.User;
import com.revature.repositories.ProductRepository;
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
import java.util.Optional;

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
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository pr;

    @BeforeEach
    public void resetDB() {
        pr.deleteAll();
    }

    private ObjectMapper om = new ObjectMapper();

//    @Test
//    @Transactional
//    public void updateProductTest()throws Exception{
//        Optional<Product> p = Optional.of(new Product(0, 1, 1.0, "a", "b", "c", false, false));
//        //HttpSession session = new MockHttpSession();
//        //User u = new User(0,"test","pass","a","b",false);
//        //pr.saveAndFlush(u);
//        mockMvc.perform(put("/api/product").contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(p))
//                ).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(1))
//                .andExpect(jsonPath("$.quantity").value(1)).andExpect(jsonPath("$.price").value(1.0))
//                .andExpect(jsonPath("$.description").value("a")).andExpect(jsonPath("$.image").value("b"))
//                .andExpect(jsonPath("$.name").value("c")).andExpect(jsonPath("$.discontinued").value(false))
//                .andExpect(jsonPath("$.featured").value(false));
//    }
}
