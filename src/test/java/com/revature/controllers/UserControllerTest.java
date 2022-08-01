package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.revature.dtos.UserRequest;
import com.revature.models.User;
import com.revature.services.UserService;

@AutoConfigureJsonTesters
@WebMvcTest(UserController.class)
public class UserControllerTest {
	
	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<User> jsonUser;

	@Autowired
	private JacksonTester<UserRequest> jsonUserRequest;
	
	@MockBean
	private UserService uServ;
	
	@InjectMocks
	private UserController controller;
	
	private final String MAPPING_ROOT = "/api/users";
	private User dummyUser;
	
	@BeforeEach
	void setUp() {
		this.dummyUser = new User(1, "dummy@revature.com", "asdf", "Dummy", "User", "Customer", null, null, null);
	}
	
	@AfterEach
	void tearDown() {
		this.dummyUser = null;
	}
	
	@Test
	void testGetUserById_Success() throws Exception {
		int userId = this.dummyUser.getId();
		given(this.uServ.findById(userId)).willReturn(Optional.of(this.dummyUser));
		
		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/" + userId).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonUser.write(this.dummyUser).getJson(), response.getContentAsString());
		verify(this.uServ, times(1)).findById(userId);
	}
	
	@Test
	void testGetUserById_Failure_UserNotFound() throws Exception {
		int id = this.dummyUser.getId();
		given(this.uServ.findById(id)).willReturn(Optional.empty());
		
		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/" + id).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
		verify(this.uServ, times(1)).findById(id);
	}
	
	@Test
	void testGetUserByEmail_Success() throws Exception {
		String email = this.dummyUser.getEmail();
		given(this.uServ.findByEmail(email)).willReturn(Optional.of(this.dummyUser));
		
		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/email/" + email).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonUser.write(this.dummyUser).getJson(), response.getContentAsString());
		verify(this.uServ, times(1)).findByEmail(email);
	}
	
	@Test
	void testGetUserByEmail_Failure_UserNotFound() throws Exception {
		String email = this.dummyUser.getEmail();
		given(this.uServ.findByEmail(email)).willReturn(Optional.empty());
		
		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/email/" + email).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
		verify(this.uServ, times(1)).findByEmail(email);
	}
	
	@Test
	void testUpdate_Success() throws Exception {
		UserRequest newUser = new UserRequest(this.dummyUser.getId(), this.dummyUser.getEmail(),
				this.dummyUser.getPassword(), this.dummyUser.getFirstName(), this.dummyUser.getLastName(),
				this.dummyUser.getRole());
		given(this.uServ.save(this.dummyUser)).willReturn(this.dummyUser);
		
		String jsonContent = this.jsonUserRequest.write(newUser).getJson();
		MockHttpServletRequestBuilder request = put(this.MAPPING_ROOT).contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent).sessionAttr("user", this.dummyUser);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonUser.write(this.dummyUser).getJson(), response.getContentAsString());
		verify(this.uServ, times(1)).save(this.dummyUser);
	}
}
