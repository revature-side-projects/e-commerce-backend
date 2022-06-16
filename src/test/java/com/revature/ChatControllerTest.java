/*
package com.revature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.controllers.ChatController;
import com.revature.models.Message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpSession;
//import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.transaction.annotation.Transactional;

//import javax.servlet.http.HttpSession;

import static com.revature.models.Status.*;
//import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//@DirtiesContext(classMode= DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ECommerceApplication.class)
//@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ChatControllerTest {

//    @Autowired
//    private MockMvc mockMvc;

    @Autowired
     private ChatController cc;

//    @BeforeEach
//    public void resetDB() {
//        pr.deleteAll();
//    }

    private ObjectMapper om = new ObjectMapper();

    @Test
    public void receiveJOINMessageTest() throws Exception{

        Message m = new Message("a","b","c","d",JOIN);
        Message test = cc.receiveMessage(m);
        assertEquals("b",test.getReceiverName(),"pass");
        assertEquals("a",test.getSenderName(),"pass");
        assertEquals("d",test.getDate(),"pass");
        assertEquals("c",test.getMessage(),"pass");
        assertEquals(JOIN,test.getStatus(),"pass");


    }

    @Test
    public void receiveMESSAGEMessageTest() throws Exception{

        Message m = new Message("a","b","c","d",MESSAGE);
        Message test = cc.receiveMessage(m);
        assertEquals("b",test.getReceiverName(),"pass");
        assertEquals("a",test.getSenderName(),"pass");
        assertEquals("d",test.getDate(),"pass");
        assertEquals("c",test.getMessage(),"pass");
        assertEquals(MESSAGE,test.getStatus(),"pass");


    }

    @Test
    public void receiveLEAVEMessageTest() throws Exception{

        Message m = new Message("a","b","c","d",LEAVE);
        Message test = cc.receiveMessage(m);
        assertEquals("b",test.getReceiverName(),"pass");
        assertEquals("a",test.getSenderName(),"pass");
        assertEquals("d",test.getDate(),"pass");
        assertEquals("c",test.getMessage(),"pass");
        assertEquals(LEAVE,test.getStatus(),"pass");


    }

    @Test
    public void recMessageTest() throws Exception{

        Message m = new Message("a","b","c","d",MESSAGE);
        Message test = cc.recMessage(m);
        assertEquals("b",test.getReceiverName(),"pass");
        assertEquals("a",test.getSenderName(),"pass");
        assertEquals("d",test.getDate(),"pass");
        assertEquals("c",test.getMessage(),"pass");
        assertEquals(MESSAGE,test.getStatus(),"pass");


    }

}
*/
