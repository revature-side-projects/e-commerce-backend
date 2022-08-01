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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.revature.config.TestConfig;
import com.revature.dtos.CreateUpdateRequest;
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
@ContextConfiguration(classes = TestConfig.class)
@WebMvcTest(ProductController.class)
class ProductControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<Product> jsonProduct;

	@Autowired
	private JacksonTester<List<Product>> jsonProductList;

	@Autowired
	private JacksonTester<List<ProductInfo>> jsonProductInfoList;

	@Autowired
	private JacksonTester<PriceRangeRequest> jsonPriceRangeRequest;

	@Autowired
	private JacksonTester<CreateUpdateRequest> jsonCreateUpdateRequest;

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

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT).accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
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

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/" + id).accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
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
	void testInsertAndUpdate_Success_InsertProduct() throws Exception {
		int id = this.dummyProduct.getId();
		CreateUpdateRequest createRequest = new CreateUpdateRequest(id, this.dummyProduct.getQuantity(),
				this.dummyProduct.getPrice(), this.dummyProduct.getDescription(), this.dummyProduct.getImage(),
				this.dummyProduct.getName());
		Product newProduct = new Product(createRequest.getQuantity(), createRequest.getPrice(),
				createRequest.getDescription(), createRequest.getImage(), createRequest.getName());

		given(this.pServ.findById(id)).willReturn(Optional.empty());
		given(this.pServ.save(newProduct)).willReturn(this.dummyProduct);

		Product expected = this.dummyProduct;
		String jsonContent = this.jsonCreateUpdateRequest.write(createRequest).getJson();
		MockHttpServletRequestBuilder request = put(this.MAPPING_ROOT + "/create-update")
				.contentType(MediaType.APPLICATION_JSON).content(jsonContent)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonProduct.write(expected).getJson(), response.getContentAsString());
		verify(this.pServ, times(1)).findById(id);
		verify(this.pServ, times(1)).save(newProduct);
	}

	@Test
	void testInsertAndUpdate_Success_UpdateProduct_AllArgs() throws Exception {
		int id = this.dummyProduct.getId();
		CreateUpdateRequest updateRequest = new CreateUpdateRequest(id, this.dummyProduct.getQuantity(),
				this.dummyProduct.getPrice(), this.dummyProduct.getDescription(), this.dummyProduct.getImage(),
				this.dummyProduct.getName());

		given(this.pServ.findById(id)).willReturn(Optional.of(this.dummyProduct));
		given(this.pServ.save(this.dummyProduct)).willReturn(this.dummyProduct);

		Product expected = this.dummyProduct;
		String jsonContent = this.jsonCreateUpdateRequest.write(updateRequest).getJson();
		MockHttpServletRequestBuilder request = put(this.MAPPING_ROOT + "/create-update")
				.contentType(MediaType.APPLICATION_JSON).content(jsonContent)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonProduct.write(expected).getJson(), response.getContentAsString());
		verify(this.pServ, times(1)).findById(id);
		verify(this.pServ, times(1)).save(this.dummyProduct);
	}

	@Test
	void testGetProductById_Success_UpdateProduct_DefaultArgs() throws Exception {
		int id = this.dummyProduct.getId();
		CreateUpdateRequest updateRequest = new CreateUpdateRequest(id, 0, 0, null, null, null);
		Product updatedProduct = this.dummyProduct;

		given(this.pServ.findById(id)).willReturn(Optional.of(this.dummyProduct));
		given(this.pServ.save(updatedProduct)).willReturn(this.dummyProduct);

		Product expected = this.dummyProduct;
		String jsonContent = this.jsonCreateUpdateRequest.write(updateRequest).getJson();
		MockHttpServletRequestBuilder request = put(this.MAPPING_ROOT + "/create-update")
				.contentType(MediaType.APPLICATION_JSON).content(jsonContent)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonProduct.write(expected).getJson(), response.getContentAsString());
		verify(this.pServ, times(1)).findById(id);
		verify(this.pServ, times(1)).save(updatedProduct);
	}

	@Test
	@Disabled("Not yet implemented")
	void testInsertAndUpdate_Failure_InsertProduct_ProductIdAlreadyExists() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testInsertAndUpdate_Failure_UpdateProduct_ProductNotFound() throws Exception {
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
	void testPurchase_Success() throws Exception {
		int productId = this.dummyProduct.getId();
		List<ProductInfo> metadata = new LinkedList<>();
		metadata.add(new ProductInfo(productId, 1, this.dummyProduct.getPrice(), this.dummyProduct.getDescription(),
				this.dummyProduct.getImage(), this.dummyProduct.getName()));
		given(this.pServ.findById(productId)).willReturn(Optional.of(this.dummyProduct));

		List<Product> productList = new LinkedList<>();
		productList.add(this.dummyProduct);
		given(this.pServ.saveAll(productList, metadata)).willReturn(productList);

		String jsonContent = this.jsonProductInfoList.write(metadata).getJson();
		MockHttpServletRequestBuilder request = patch(this.MAPPING_ROOT).contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonProductList.write(productList).getJson(), response.getContentAsString());
		verify(this.pServ, times(1)).findById(productId);
		verify(this.pServ, times(1)).saveAll(productList, metadata);
	}

	@Test
	@Disabled("Not yet implemented")
	void testPurchase_Failure_ProductNotFound() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testPurchase_Failure_BadRequestInvalidQuantity() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteProduct_Success() throws Exception {
		int productId = this.dummyProduct.getId();
		given(this.pServ.findById(productId)).willReturn(Optional.of(this.dummyProduct));

		MockHttpServletRequestBuilder request = delete(this.MAPPING_ROOT + "/" + productId)
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
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
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
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
				.contentType(MediaType.APPLICATION_JSON).content(content)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
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
	void testFilterByRating() throws Exception {
		// Currently we're only testing the filterByRating method to determine that the
		// endpoint is working properly
		List<Product> results = new LinkedList<>();
		results.add(this.dummyProduct);
		given(this.pServ.filterByRating()).willReturn(results);

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/filter-rating")
				.contentType(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer token");
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonProductList.write(results).getJson(), response.getContentAsString());
	}

}
