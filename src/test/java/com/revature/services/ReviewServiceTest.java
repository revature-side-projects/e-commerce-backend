package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.dtos.ReviewRequest;
import com.revature.models.Product;
import com.revature.models.Review;
import com.revature.models.User;
import com.revature.repositories.ReviewRepository;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

	@Mock
	ProductService pServ;
	@Mock
	UserService uServ;
	@Mock
	ReviewRepository mockReviewRepo;

	@InjectMocks
	ReviewService rServ;

	Product dummyProduct;
	Review dummyReview;
	User dummyUser;

	@BeforeEach
	void setUp() throws Exception {
		this.dummyProduct = new Product(1, 1, 12.34, "T-shirt", "image.jpg", "T-shirt", null, null);
		this.dummyUser = new User(1, "dummy@revature.com", "asdf", "Dummy", "User", "Customer", null, null, null);
		this.dummyReview = new Review(1, 5, "Review title", "Review body sample text", null, null, this.dummyProduct,
				this.dummyUser);

		// Set<Review> reviews = new LinkedHashSet<>();
		// reviews.add(this.dummyReview);
		// this.dummyProduct.setReviews(reviews);
	}

	@AfterEach
	void tearDown() throws Exception {
		// GC the dummy objects
		this.dummyReview = null;
		this.dummyUser = null;
		this.dummyProduct = null;
	}

	@Test
	void testAdd_Success() {
		ReviewRequest request = new ReviewRequest(this.dummyProduct.getId(), 5, "Review title",
				"Review body sample text");
		Review newReview = new Review(request.getStars(), request.getTitle(), request.getReview(), this.dummyUser,
				this.dummyProduct);
		Review expected = new Review(2, newReview.getStars(), newReview.getTitle(), newReview.getReview(),
				newReview.getPosted(), newReview.getUpdated(), this.dummyProduct, this.dummyUser);

		given(this.pServ.findById(request.getProductId())).willReturn(Optional.of(this.dummyProduct));
		given(this.mockReviewRepo.save(newReview)).willReturn(expected);

		Review actual = this.rServ.add(request, this.dummyUser);

		assertEquals(expected, actual);
		verify(this.mockReviewRepo, times(1)).save(newReview);
	}

	@Test
	@Disabled("Not yet implemented")
	void testAdd_Failure_ProductNotFound() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testFindByProductId_Success() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testFindByProductId_Failure_ProductIdNotFound() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testFindByUserId_Success() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testFindByUserId_Failure_UserIdNotFound() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testFindById() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testSave() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testUpdate_Success() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testUpdate_Failure_UnauthorizedUserId() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testDelete_Success() {
		fail("Not yet implemented");
	}

	@Test
	@Disabled("Not yet implemented")
	void testDelete_Failure_UnauthorizedUserId() {
		fail("Not yet implemented");
	}

}
