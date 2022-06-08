package com.revature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.dtos.LoginRequest;
import com.revature.dtos.ProductInfo;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

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

    @Test
    @Transactional
    public void updateProductTest()throws Exception{
        Product p = new Product();
        User u = new User();
//        p.setId(0);
//        p.setQuantity(1);
//        p.setDescription("a");
//        p.setPrice(1.0);
//        p.setImage("b");
//        p.setDiscontinued(false);
//        p.setFeatured(false);
//        p.setName("c");
//        u.setId(0);
//        u.setEmail("test");
//        u.setPassword("pass");
//        u.setFirstName("a");
//        u.setLastName("b");
//        u.setAdmin(false);
        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        sessionAttr.put("user", u);
        mockMvc.perform(put("/api/product").sessionAttrs(sessionAttr).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(p))
                ).andDo(print()).andExpect(status().isOk());
//                .andExpect(jsonPath("$.id").value(0))
//                .andExpect(jsonPath("$.quantity").value(1)).andExpect(jsonPath("$.price").value(1.0))
//                .andExpect(jsonPath("$.description").value("a")).andExpect(jsonPath("$.image").value("b"))
//                .andExpect(jsonPath("$.name").value("c"))
//                .andExpect(jsonPath("$.discontinued").value(false))
//                .andExpect(jsonPath("$.featured").value(false));
    }

    @Test
    @Transactional
    public void getInventoryTest()throws Exception{

        //Product p = new Product();
        User u = new User();

        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        sessionAttr.put("user", u);
        mockMvc.perform(get("/api/product").sessionAttrs(sessionAttr)
        ).andDo(print()).andExpect(status().isOk());
//                .andExpect(jsonPath("$.[0].id").value(1))
//                .andExpect(jsonPath("$.[0].quantity").value(10)).andExpect(jsonPath("$.[0].price").value(20.0))
//                .andExpect(jsonPath("$.[0].description").value("A nice pair of headphones")).andExpect(jsonPath("$.[0].image").value("https://i.insider.com/54eb437f6bb3f7697f85da71?width=1000&format=jpeg&auto=webp"))
//                .andExpect(jsonPath("$.[0].name").value("Headphones"))
//                .andExpect(jsonPath("$.[0].discontinued").value(false))
//                .andExpect(jsonPath("$.[0].featured").value(false));
    }

    @Test
    @Transactional
    public void getItemByIdFailedTest()throws Exception {

        //Product p = new Product();
        User u = new User();

        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        sessionAttr.put("user", u);
        mockMvc.perform(get("/api/product/1").sessionAttrs(sessionAttr)
        ).andDo(print()).andExpect(status().isNotFound());
    }

//    @Test
//    @Transactional
//    public void purchaseFailedTest()throws Exception {
//        List<ProductInfo> lp = new ArrayList<ProductInfo>();
//        ProductInfo p = new ProductInfo();
//        lp.add(p);
//        User u = new User();
//
//        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
//        sessionAttr.put("user", u);
//        mockMvc.perform(patch("/api/product/").sessionAttrs(sessionAttr).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsString(lp))
//        ).andDo(print()).andExpect(status().isNotFound());
//    }

    @Test
    @Transactional
    public void deleteFailedTest()throws Exception {

        //Product p = new Product();
        User u = new User();

        HashMap<String, Object> sessionAttr = new HashMap<String, Object>();
        sessionAttr.put("user", u);
        mockMvc.perform(delete("/api/product/1").sessionAttrs(sessionAttr)
        ).andDo(print()).andExpect(status().isNotFound());
    }

}
