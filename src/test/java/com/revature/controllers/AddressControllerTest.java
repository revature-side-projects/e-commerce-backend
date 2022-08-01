package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

import com.revature.dtos.AddressRequest;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Address;
import com.revature.models.User;
import com.revature.services.AddressService;
import com.revature.services.UserService;

@AutoConfigureJsonTesters
@WebMvcTest(AddressController.class)
class AddressControllerTest {
	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private JacksonTester<Address> jsonAddress;
	
	@Autowired
	private JacksonTester<Set<Address>> jsonReviewList;
	
	@Autowired
	private JacksonTester<AddressRequest> JsonAddressRequest;
	
	@MockBean
	private AddressService aserv;
	
	@MockBean
	private UserService userv;
	
	@InjectMocks
	private AddressController controller;
	
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
		dummyUser.setAddresses(addresses);
		
		given(this.userv.findById(dummyUser.getId())).willReturn(Optional.of(dummyUser));
		given(this.aserv.findUsersAddresses(dummyUser)).willReturn(dummyUser.getAddresses());
		
		MockHttpServletRequestBuilder request = get(this.MAPPING + "/1").accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonReviewList.write(addresses).getJson(), response.getContentAsString());
		verify(this.aserv, times(1)).findUsersAddresses(dummyUser);
	}
	
	@Test
	void testGetUserAddress_Fail_UserNotFound() throws Exception{
		
		Set<Address> expected = new HashSet<Address>();
		expected.add(dummyAddress);
		
		given(userv.findById(dummyUser.getId())).willThrow(UserNotFoundException.class);
		given(aserv.findUsersAddresses(dummyUser)).willThrow(NullPointerException.class);
		MockHttpServletRequestBuilder request = get(MAPPING + "/1").accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
		assertNotEquals(this.jsonReviewList.write(expected).getJson(), response.getContentAsString());
		verify(this.userv, times(1)).findById(1);
		verify(this.aserv, times(0)).findUsersAddresses(dummyUser);
	}
	
	@Test
	void testGetUserAddress_NoAddress() throws Exception{
		given(userv.findById(dummyUser.getId())).willReturn(Optional.of(dummyUser));
		MockHttpServletRequestBuilder request = get(MAPPING + "/1").accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = mvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertNotEquals("<>", response.getContentAsString());
		verify(this.userv, times(1)).findById(1);
		verify(this.aserv, times(1)).findUsersAddresses(dummyUser);
	}
	
	@Test
	void testUpdateAddress_Succeed() throws Exception {
		AddressRequest newReq = new AddressRequest();
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(dummyAddress);
		dummyUser.setAddresses(addresses);
		
		newReq.setStreet("105 maybarry");
		newReq.setSecondary("");
		newReq.setCity("Far Far Away");
		newReq.setZip("70530");
		newReq.setState("NJ");
		
		given(aserv.update(newReq, dummyUser)).willReturn(dummyAddress);

		String jsonContent = this.JsonAddressRequest.write(newReq).getJson();
		
		MockHttpServletRequestBuilder request = put(MAPPING + "/1").contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent).sessionAttr("user", dummyUser);
		MockHttpServletResponse response = mvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonAddress.write(dummyAddress).getJson(), response.getContentAsString());
		verify(this.aserv, times(1)).update(newReq, dummyUser);
	}
	
	@Test
	void testUpdateAddress_Fail_NoAddresses() throws Exception{
		AddressRequest newReq = new AddressRequest();
		Set<Address> addresses = new HashSet<Address>();
		addresses.add(dummyAddress);
		
		newReq.setStreet(dummyAddress.getStreet());
		newReq.setSecondary(dummyAddress.getSecondary());
		newReq.setCity(dummyAddress.getCity());
		newReq.setZip(dummyAddress.getZip());
		newReq.setState(dummyAddress.getState());
		
		String jsonContent = this.JsonAddressRequest.write(newReq).getJson();
		
		MockHttpServletRequestBuilder request = put(MAPPING + "/1").contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent).sessionAttr("user", dummyUser);
		MockHttpServletResponse response = mvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals("", response.getContentAsString());
		verify(this.aserv, times(1)).update(newReq, dummyUser);
	}
	
	
	@Test
	void testAddAddress_Success() throws Exception{
		AddressRequest newReq = new AddressRequest();
		newReq.setStreet("105 maybarry");
		newReq.setSecondary("");
		newReq.setCity("Far Far Away");
		newReq.setZip("70530");
		newReq.setState("NJ");
		
		given(this.aserv.addAddress(newReq, this.dummyUser)).willReturn(this.dummyAddress);
		String jsonContent = this.JsonAddressRequest.write(newReq).getJson();
		
		MockHttpServletRequestBuilder request = post(MAPPING).contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent).sessionAttr("user", dummyUser);
		MockHttpServletResponse response = mvc.perform(request).andReturn().getResponse();
		
		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonAddress.write(dummyAddress).getJson(), response.getContentAsString());
		verify(this.aserv, times(1)).addAddress(newReq, dummyUser);
	}
}
