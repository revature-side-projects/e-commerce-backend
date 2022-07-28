package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.*;
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

import com.revature.dtos.PriceRangeRequest;
import com.revature.dtos.ProductInfo;
import com.revature.models.Product;
import com.revature.services.ProductService;
import com.revature.services.StorageService;

/**
 *
 * Unit tests for the {@link ProductController} class.
 *
 * @see <a href=
 *      "https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-2-spring-mockmvc-example-with-webapplicationcontext">https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-2-spring-mockmvc-example-with-webapplicationcontext</a>
 *
 */
@AutoConfigureJsonTesters
@WebMvcTest(ProductController.class)
class ProductControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<Product> jsonProduct;

	@Autowired
	private JacksonTester<List<Product>> jsonProductList;

	@Autowired
	private JacksonTester<ProductInfo> jsonProductInfo;

	@Autowired
	private JacksonTester<PriceRangeRequest> jsonPriceRangeRequest;

	@MockBean
	private ProductService pServ;

	@MockBean
	private StorageService s3Serv;

	@InjectMocks
	private ProductController controller;

	private final String MAPPING_ROOT = "/api/product";
	private Product dummyProduct;

	@BeforeEach
	void setUp() throws Exception {
		this.dummyProduct = new Product(1, 100, 9.99, "A dummy product", "image.jpg", "Dummy Merchandise", null, null);
	}

	@AfterEach
	void tearDown() throws Exception {
		this.dummyProduct = null;
	}

	@Test
	void testGetInventory_Success() throws Exception {
		List<Product> inventory = new LinkedList<>();
		inventory.add(this.dummyProduct);
		given(this.pServ.findAll()).willReturn(inventory);

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonProductList.write(inventory).getJson(), response.getContentAsString());
		verify(this.pServ, times(1)).findAll();
	}

	@Test
	@Disabled("Not yet implemented")
	void testGetInventory_Success_ListIsEmpty() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	void testGetProductById_Success() throws Exception {
		int id = this.dummyProduct.getId();
		given(this.pServ.findById(id)).willReturn(Optional.of(this.dummyProduct));

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/" + id).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonProduct.write(this.dummyProduct).getJson(), response.getContentAsString());
		verify(this.pServ, times(1)).findById(id);
	}

	@Test
	@Disabled("Not yet implemented")
	void testGetProductById_Failure_ProductNotFound() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testInsertAndUpdate_Success() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testInsertAndUpdate_Failure_ProductNotFound() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testInsertAndUpdate_Failure_UserIsNotAdmin() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testUploadImage_Success() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testUploadImage_Failure_NotLoggedIn() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testUploadImage_Failure_UserIsNotAdmin() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testPurchase_Success() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testPurchase_Failure_NotLoggedIn() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteProduct_Success() throws Exception {
		int productId = this.dummyProduct.getId();
		given(this.pServ.findById(productId)).willReturn(Optional.of(this.dummyProduct));

		MockHttpServletRequestBuilder request = delete(this.MAPPING_ROOT + "/" + productId)
				.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonProduct.write(this.dummyProduct).getJson(), response.getContentAsString());
		verify(this.pServ, times(1)).delete(productId);
	}

	@Test
	@Disabled("Not yet implemented")
	void testDeleteProduct_Failure_ProductNotFound() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testDeleteProduct_Failure_UserIsNotAdmin() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	void testGetProductsByNameContains_Success() throws Exception {
		String name = "dummy";
		List<Product> results = new LinkedList<>();
		results.add(this.dummyProduct);
		given(this.pServ.findByNameContains(name)).willReturn(results);

		List<Product> expected = results;
		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/partial-search/" + name)
				.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonProductList.write(expected).getJson(), response.getContentAsString());
		verify(this.pServ, times(1)).findByNameContains(name);
	}

	@Test
	@Disabled("Not yet implemented")
	void testGetProductsByNameContains_Failure_ProductNotFound() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	void testGetProductsByPriceRange_Success() throws Exception {
		double minPrice = 1.99;
		double maxPrice = 10.50;
		PriceRangeRequest range = new PriceRangeRequest(minPrice, maxPrice);
		List<Product> results = new LinkedList<>();
		results.add(this.dummyProduct);

		given(this.pServ.findByPriceRange(minPrice, maxPrice)).willReturn(results);

		List<Product> expected = results;
		String content = this.jsonPriceRangeRequest.write(range).getJson();
		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/price-range")
				.contentType(MediaType.APPLICATION_JSON).content(content);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonProductList.write(expected).getJson(), response.getContentAsString());
		verify(this.pServ, times(1)).findByPriceRange(minPrice, maxPrice);
	}

	@Test
	@Disabled("Not yet implemented")
	void testGetProductsByPriceRange_Failure_InvalidRange() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Service method implementation is incomplete")
	void testFilterByRating_Success() throws Exception {
		int rating = 5;
		List<Product> results = new LinkedList<>();
		results.add(this.dummyProduct);
		// given(this.pServ.filterByRating(rating)).willReturn(results);

		List<Product> expected = results;
		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/filter-rating/" + rating)
				.contentType(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonProductList.write(expected).getJson(), response.getContentAsString());
		// verify(this.pServ, times(1)).filterByRating(rating);
	}

	@Test
	@Disabled("Not yet implemented")
	void testFilterByRating_Failure_RatingOutOfBounds() throws Exception {
		fail("Not yet implemented");
	}

}
