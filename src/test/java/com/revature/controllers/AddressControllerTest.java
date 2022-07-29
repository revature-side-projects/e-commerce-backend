package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.revature.dtos.AddressRequest;
import com.revature.models.Address;
import com.revature.models.User;
import com.revature.services.AddressService;
import com.revature.services.UserService;

@AutoConfigureJsonTesters
@WebMvcTest(AddressController.class)
public class AddressControllerTest {
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<Address> jsonReview;
	
	@Autowired
	private JacksonTester<Set<Address>> jsonReviewList;
	
	@Autowired
	private JacksonTester<AddressRequest> JsonReviewRequest;
	
	@MockBean
	private AddressService aserv;
	
	@MockBean
	private UserService userv;
	
	@InjectMocks
	private AddressController controller;
	private Logger log = LoggerFactory.getLogger(AddressControllerTest.class);
	
	private final String MAPPING = "/api/addresses";
	private Address dummyAddress;
	private User dummyUser;
	
	@BeforeEach
	void setUp() {
		this.dummyAddress = new Address(1, "6803 N.Navarro st", "apt 332", "Victoria", "77904", "Texas", null);
		this.dummyUser = new User(1, "email@email.com", "abx", "Primary", "User" , "Customer", null, null, null);
	}
	
	@AfterEach
	void tearDown() {
		this.dummyUser = null;
		this.dummyAddress = null;
	}
	
	@Test
	void testGetUserAddress_Success() throws Exception{
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(this.dummyAddress);
		System.out.println(addresses.toString());
		dummyUser.setAddresses(addresses);
		System.out.println(dummyUser.getAddresses());
		
		given(this.userv.findById(1).get()).willReturn(dummyUser);
		given(this.aserv.findUsersAddresses(dummyUser)).willReturn(dummyUser.getAddresses());
		
		MockHttpServletRequestBuilder request = get(this.MAPPING + "/1").accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonReviewList.write(addresses).getJson(), response.getContentAsString());
		verify(this.aserv, times(1)).findUsersAddresses(dummyUser);
	}
}
