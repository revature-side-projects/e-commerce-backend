package com.revature.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.LinkedList;
import java.util.List;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import com.revature.dtos.ReviewRequest;
import com.revature.exceptions.ReviewNotFoundException;
import com.revature.models.Product;
import com.revature.models.Review;
import com.revature.models.User;
import com.revature.services.ReviewService;

/**
 *
 * Unit tests for the {@link ReviewController} class.
 *
 * @see <a href=
 *      "https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-2-spring-mockmvc-example-with-webapplicationcontext">https://thepracticaldeveloper.com/guide-spring-boot-controller-tests/#strategy-2-spring-mockmvc-example-with-webapplicationcontext</a>
 *
 */
@AutoConfigureJsonTesters
@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private JacksonTester<Review> jsonReview;

	@Autowired
	private JacksonTester<List<Review>> jsonReviewList;

	@Autowired
	private JacksonTester<ReviewRequest> jsonReviewRequest;

	@MockBean
	private ReviewService rServ;

	@InjectMocks
	private ReviewController controller;

	private final String MAPPING_ROOT = "/api/review";
	private Review dummyReview;
	private User dummyUser;
	private Product dummyProduct;

	@BeforeEach
	void setUp() throws Exception {
		this.dummyProduct = new Product(1, 1, 12.34, "T-shirt", "image.jpg", "T-shirt", null, null);
		this.dummyUser = new User(1, "dummy@revature.com", "asdf", "Dummy", "User", "Customer", null, null, null);
		this.dummyReview = new Review(1, 5, "Review title", "Review body sample text", null, null, this.dummyProduct,
				this.dummyUser);
	}

	@AfterEach
	void tearDown() throws Exception {
		// GC the dummy objects
		this.dummyReview = null;
		this.dummyUser = null;
		this.dummyProduct = null;
	}

	@Test
	void testGetReviews_Success() throws Exception {
		List<Review> reviews = new LinkedList<>();
		reviews.add(this.dummyReview);
		// TODO Add more dummy reviews

		given(this.rServ.findAll()).willReturn(reviews);

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonReviewList.write(reviews).getJson(), response.getContentAsString());
		verify(this.rServ, times(1)).findAll();
	}

	@Test
	void testGetReviews_Success_ListIsEmpty() throws Exception {
		List<Review> expected = new LinkedList<>();
		given(this.rServ.findAll()).willReturn(expected);

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonReviewList.write(expected).getJson(), response.getContentAsString());
		verify(this.rServ, times(1)).findAll();
	}

	@Test
	void testGetReviewsOfProduct_Success() throws Exception {
		int productId = this.dummyProduct.getId();
		List<Review> reviews = new LinkedList<>();
		reviews.add(this.dummyReview);
		given(this.rServ.findByProductId(productId)).willReturn(reviews);

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/product/" + productId)
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonReviewList.write(reviews).getJson(), response.getContentAsString());
		verify(this.rServ, times(1)).findByProductId(productId);
	}

	@Test
	@Disabled("Not yet implemented")
	void testGetReviewsOfProduct_Failure_ProductNotFound() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	void testGetReviewsByUser_Success() throws Exception {
		int userId = this.dummyUser.getId();
		List<Review> reviews = new LinkedList<>();
		reviews.add(this.dummyReview);
		given(this.rServ.findByUserId(userId)).willReturn(reviews);

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/user/" + userId)
				.accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonReviewList.write(reviews).getJson(), response.getContentAsString());
		verify(this.rServ, times(1)).findByUserId(userId);
	}

	@Test
	@Disabled("Not yet implemented")
	void testGetReviewsByUser_Failure_UserNotFound() throws Exception {
		fail("Not yet implemented");
	}

	@Test
	void testGetReviewById_Success() throws Exception {
		int id = this.dummyReview.getId();
		given(this.rServ.findById(id)).willReturn(this.dummyReview);

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/" + id).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonReview.write(this.dummyReview).getJson(), response.getContentAsString());
		verify(this.rServ, times(1)).findById(id);
	}

	@Test
	void testGetReviewById_Failure_ReviewNotFound() throws Exception {
		int id = this.dummyReview.getId();
		given(this.rServ.findById(id)).willThrow(ReviewNotFoundException.class);

		MockHttpServletRequestBuilder request = get(this.MAPPING_ROOT + "/" + id).accept(MediaType.APPLICATION_JSON);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
		verify(this.rServ, times(1)).findById(id);
	}

	@Test
	void testAddReview_Success() throws Exception {
		int authorId = this.dummyUser.getId();
		ReviewRequest newReview = new ReviewRequest(authorId, this.dummyProduct.getId(), this.dummyReview.getStars(),
				this.dummyReview.getTitle(), this.dummyReview.getReview());
		given(this.rServ.add(newReview)).willReturn(this.dummyReview);

		String jsonContent = this.jsonReviewRequest.write(newReview).getJson();
		MockHttpServletRequestBuilder request = post(this.MAPPING_ROOT).contentType(MediaType.APPLICATION_JSON)
				.content(jsonContent).sessionAttr("user", this.dummyUser);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonReview.write(this.dummyReview).getJson(), response.getContentAsString());
		verify(this.rServ, times(1)).add(newReview);
	}

	@Test
	@Disabled("Not yet implemented")
	void testAddReview_Failure_NotLoggedIn() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testAddReview_Failure_ProductNotFound() {
		fail("Not yet implemented");
	}

	@Test
	void testUpdateReview_Success() throws Exception {
		int reviewId = this.dummyReview.getId();
		int authorId = this.dummyUser.getId();
		ReviewRequest updatedReview = new ReviewRequest(authorId, this.dummyProduct.getId(), 5, "Updated review",
				"This new version of the product made it a lot better");
		this.dummyReview.setTitle(updatedReview.getTitle());
		this.dummyReview.setReview(updatedReview.getReview());
		this.dummyReview.setStars(updatedReview.getStars());

		given(this.rServ.update(updatedReview, reviewId)).willReturn(this.dummyReview);

		String jsonContent = this.jsonReviewRequest.write(updatedReview).getJson();
		MockHttpServletRequestBuilder request = put(this.MAPPING_ROOT + "/" + reviewId)
				.contentType(MediaType.APPLICATION_JSON).content(jsonContent).sessionAttr("user", this.dummyUser);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		assertEquals(this.jsonReview.write(this.dummyReview).getJson(), response.getContentAsString());
		verify(this.rServ, times(1)).update(updatedReview, reviewId);
	}

	@Test
	@Disabled("Not yet implemented")
	void testUpdateReview_Failure_ReviewNotFound() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testUpdateReview_Failure_UnauthorizedUser() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testUpdateReview_Failure_NotLoggedIn() {
		fail("Not yet implemented");
	}

	@Test
	void testDeleteReview_Success() throws Exception {
		int reviewId = this.dummyReview.getId();

		MockHttpServletRequestBuilder request = delete(this.MAPPING_ROOT + "/" + reviewId)
				.contentType(MediaType.APPLICATION_JSON).sessionAttr("user", this.dummyUser);
		MockHttpServletResponse response = this.mvc.perform(request).andReturn().getResponse();

		assertEquals(HttpStatus.OK.value(), response.getStatus());
		verify(this.rServ, times(1)).delete(reviewId, this.dummyUser.getId());
	}

	@Test
	@Disabled("Not yet implemented")
	void testDeleteReview_Failure_UserUnauthorized() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testDeleteReview_Failure_NotLoggedIn() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testDeleteReview_Failure_ReviewNotFound() {
		fail("Not yet implemented");
	}

}
