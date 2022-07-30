package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

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

import com.revature.dtos.PurchaseRequest;
import com.revature.exceptions.ProductNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Product;
import com.revature.models.Purchase;
import com.revature.models.User;
import com.revature.services.PurchaseService;

@AutoConfigureJsonTesters
@WebMvcTest(PurchaseController.class)
class PurchaseControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<Purchase> jsonPurchase;

	@Autowired
	private JacksonTester<List<Purchase>> jsonPurchaseList;

	@Autowired
	private JacksonTester<List<PurchaseRequest>> jsonPurchaseRequestList;

	@MockBean
	private PurchaseService pServ;

	@InjectMocks
	private PurchaseController controller;

	private final String MAPPING_ROOT = "/api/purchases";
	private User dummyUser;
	private Product dummyProduct;
	private Purchase dummyPurchase;

	@BeforeEach
	void setUp() throws Exception {
		this.dummyProduct = new Product();
		this.dummyUser = new User(1, "dummy@revature.com", "asdf", "Dummy", "User", "Customer", null, null, null);
		this.dummyPurchase = new Purchase(1, new Timestamp(1234567890000L), this.dummyProduct, this.dummyUser, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		// GC the dummy objects
		this.dummyProduct = null;
		this.dummyUser = null;
		this.dummyPurchase = null;
	}

	@Test
	void testGetAllPurchases() throws Exception {
		List<Purchase> purchases = new LinkedList<>();
		purchases.add(this.dummyPurchase);

		given(this.pServ.findAll()).willReturn(purchases);

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonPurchaseList.write(purchases).getJson(), response.getContentAsString());
		verify(this.pServ, times(1)).findAll();
	}

	@Test
	void testGetPurchasesByOwner_Success() throws Exception {
		int userId = this.dummyUser.getId();
		List<Purchase> purchases = new LinkedList<>();
		purchases.add(this.dummyPurchase);
		given(this.pServ.findByOwner(userId)).willReturn(purchases);

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/user/" + userId)
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonPurchaseList.write(purchases).getJson(), response.getContentAsString());
		verify(this.pServ, times(1)).findByOwner(userId);
	}

	@Test
	void testGetPurchasesByOwner_Failure_UserNotFound() throws Exception {
		int userId = this.dummyUser.getId();
		given(this.pServ.findByOwner(userId)).willThrow(new UserNotFoundException(userId));

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/user/" + userId)
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
		verify(this.pServ, times(1)).findByOwner(userId);
	}

	@Test
	void testAddPurchase_Success() throws Exception {
		PurchaseRequest newPurchase = new PurchaseRequest(this.dummyProduct.getId(), this.dummyPurchase.getQuantity());
		List<PurchaseRequest> addRequests = new LinkedList<>();
		addRequests.add(newPurchase);
		given(this.pServ.add(newPurchase, this.dummyUser)).willReturn(this.dummyPurchase);

		List<Purchase> expected = new LinkedList<>();
		expected.add(this.dummyPurchase);

		String jsonContent = this.jsonPurchaseRequestList.write(addRequests).getJson();
		MockHttpServletRequestBuilder request = post(this.MAPPING_ROOT).contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent).sessionAttr("user", this.dummyUser);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonPurchaseList.write(expected).getJson(), response.getContentAsString());
		verify(this.pServ, times(1)).add(newPurchase, this.dummyUser);
	}

	@Test
	void testAddPurchase_Failure_ProductNotFound() throws Exception {
		int productId = this.dummyProduct.getId();
		PurchaseRequest newPurchase = new PurchaseRequest(productId, this.dummyPurchase.getQuantity());
		List<PurchaseRequest> addRequests = new LinkedList<>();
		addRequests.add(newPurchase);
		given(this.pServ.add(newPurchase, this.dummyUser)).willThrow(new ProductNotFoundException(productId));

		List<Purchase> expected = new LinkedList<>();
		expected.add(this.dummyPurchase);

		String jsonContent = this.jsonPurchaseRequestList.write(addRequests).getJson();
		MockHttpServletRequestBuilder request = post(this.MAPPING_ROOT).contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent).sessionAttr("user", this.dummyUser);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		verify(this.pServ, times(1)).add(newPurchase, this.dummyUser);
		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}

}
