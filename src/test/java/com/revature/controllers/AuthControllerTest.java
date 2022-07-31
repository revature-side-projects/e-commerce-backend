package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.util.HashSet;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.revature.dtos.LoginRequest;
import com.revature.dtos.RegisterRequest;
import com.revature.models.User;
import com.revature.services.AuthService;

@AutoConfigureJsonTesters
@WebMvcTest(AuthController.class)
class AuthControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<User> jsonUser;

	@Autowired
	private JacksonTester<LoginRequest> jsonLoginRequest;

	@Autowired
	private JacksonTester<RegisterRequest> jsonRegisterRequest;

	@MockBean
	private AuthService aServ;

	@InjectMocks
	private AuthController controller;

	private final String MAPPING_ROOT = "/auth";
	private User dummyUser;

	@BeforeEach
	void setUp() throws Exception {
		this.dummyUser = new User(1, "dummy@revature.com", "asdf", "Dummy", "User", "CUSTOMER", null, null, null);
	}

	@AfterEach
	void tearDown() throws Exception {
		this.dummyUser = null;
	}

	@Test
	void testLogin_Success() throws Exception {
		String email = this.dummyUser.getEmail();
		String password = this.dummyUser.getPassword();
		LoginRequest loginRequest = new LoginRequest(email, password);
		given(this.aServ.findByCredentials(email, password)).willReturn(Optional.of(this.dummyUser));

		String requestContent = this.jsonLoginRequest.write(loginRequest).getJson();
		MockHttpServletRequestBuilder request = post(this.MAPPING_ROOT + "/login")
				.contentType(MediaType.APPLICATION_JSON).content(requestContent);
		MvcResult result = this.mvc.perform(request).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonUser.write(this.dummyUser).getJson(), response.getContentAsString());
		assertEquals(this.dummyUser, result.getRequest().getSession().getAttribute("user"));
		verify(this.aServ, times(1)).findByCredentials(email, password);
	}

	@Test
	void testLogin_Failure_WrongCredentials() throws Exception {
		String email = this.dummyUser.getEmail();
		String password = this.dummyUser.getPassword();
		LoginRequest loginRequest = new LoginRequest(email, password);
		given(this.aServ.findByCredentials(email, password)).willReturn(Optional.empty());

		String requestContent = this.jsonLoginRequest.write(loginRequest).getJson();
		MockHttpServletRequestBuilder request = post(this.MAPPING_ROOT + "/login")
				.contentType(MediaType.APPLICATION_JSON).content(requestContent);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
		verify(this.aServ, times(1)).findByCredentials(email, password);
	}

	@Test
	void testLogout() throws Exception {
		String attrName = "user";
		MockHttpServletRequestBuilder request = post(this.MAPPING_ROOT + "/logout").sessionAttr(attrName,
				this.dummyUser);
		MvcResult result = this.mvc.perform(request).andReturn();
		MockHttpServletResponse response = result.getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNull(result.getRequest().getSession().getAttribute(attrName));
	}

	@Test
	void testRegister_Success() throws Exception {
		RegisterRequest registerRequest = new RegisterRequest(this.dummyUser.getEmail(), this.dummyUser.getPassword(),
				this.dummyUser.getFirstName(), this.dummyUser.getLastName());
		User newUser = new User(0, registerRequest.getEmail(), registerRequest.getPassword(),
				registerRequest.getFirstName(), registerRequest.getLastName(), "CUSTOMER", new HashSet<>(),
				new HashSet<>(), new HashSet<>());
		given(this.aServ.register(newUser)).willReturn(this.dummyUser);

		String requestContent = this.jsonRegisterRequest.write(registerRequest).getJson();
		MockHttpServletRequestBuilder request = post(this.MAPPING_ROOT + "/register")
				.contentType(MediaType.APPLICATION_JSON).content(requestContent);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.CREATED.value(), response.getStatus());
		assertEquals(this.jsonUser.write(this.dummyUser).getJson(), response.getContentAsString());
		verify(this.aServ, times(1)).register(newUser);
	}

	@Test
	@Disabled("Not yet implemented")
	void testRegister_Failure_EmailAlreadyExists() throws Exception {
		fail("Not yet implemented");
	}

}
