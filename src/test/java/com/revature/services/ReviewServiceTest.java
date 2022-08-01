package com.revature.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.revature.dtos.ReviewRequest;
import com.revature.exceptions.DuplicateReviewException;
import com.revature.exceptions.ReviewNotFoundException;
import com.revature.exceptions.UnauthorizedReviewAccessException;
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
		int authorId = this.dummyUser.getId();
		int productId = this.dummyProduct.getId();
		ReviewRequest request = new ReviewRequest(authorId, productId, 5, "Review title", "Review body sample text");
		Review newReview = new Review(request.getStars(), request.getTitle(), request.getReview(), this.dummyUser,
				this.dummyProduct);
		Review expected = new Review(2, newReview.getStars(), newReview.getTitle(), newReview.getReview(),
				newReview.getPosted(), newReview.getUpdated(), this.dummyProduct, this.dummyUser);
		given(this.pServ.findById(productId)).willReturn(Optional.of(this.dummyProduct));
		given(this.uServ.findById(authorId)).willReturn(Optional.of(this.dummyUser));
		given(this.mockReviewRepo.save(newReview)).willReturn(expected);

		Review actual = this.rServ.add(request);

		assertEquals(expected, actual);
		verify(this.pServ, times(2)).findById(productId);
		verify(this.uServ, times(1)).findById(authorId);
		verify(this.mockReviewRepo, times(1)).save(newReview);
	}

	@Test
	void testAdd_Failure_ProductNotFound() {
		ReviewRequest request = new ReviewRequest(this.dummyUser.getId(), this.dummyProduct.getId(), 1, "Not working",
				"It doesn't work as advertised. I am dissatisfied with this product.");
		int productId = this.dummyProduct.getId();
		given(this.pServ.findById(request.getProductId())).willReturn(Optional.empty());

		try {
			this.rServ.add(request);
			fail("Expected a ResourceAccessException to be thrown");
		} catch (Exception e) {
			assertEquals("No product found with ID " + productId, e.getMessage());
			verify(this.pServ, times(1)).findById(productId);
		}
	}

	@Test
	void testAdd_Failure_DuplicateReview() {
		ReviewRequest request = new ReviewRequest(this.dummyUser.getId(), this.dummyProduct.getId(), 1, "Not working",
				"It doesn't work as advertised. I am dissatisfied with this product.");
		int productId = this.dummyProduct.getId();
		given(this.pServ.findById(productId)).willReturn(Optional.of(this.dummyProduct));
		given(this.uServ.findById(this.dummyUser.getId())).willReturn(Optional.of(this.dummyUser));
		given(this.rServ.findByProductId(this.dummyProduct.getId())).willReturn(Collections.singletonList(
				new Review(2, 1, "A title", "A review of a thing.", null, null, this.dummyProduct, this.dummyUser)));
		
		try {
			this.rServ.add(request);
			fail("Expected DuplicateReviewException to be thrown");
		} catch (Exception e) {
			assertEquals(DuplicateReviewException.class, e.getClass());
		}
	}

	@Test
	void testFindAll() {
		User user2 = new User("user2@revature.com", "qwerty123", "Another", "User", "Customer");
		List<Review> expected = new LinkedList<>();
		expected.add(this.dummyReview);
		expected.add(new Review(2, 4, "Another review", "Some review body text", null, null, this.dummyProduct, user2));

		given(this.mockReviewRepo.findAll()).willReturn(expected);

		List<Review> actual = this.rServ.findAll();

		assertEquals(expected, actual);
		assertTrue(expected.containsAll(actual));
		assertEquals(expected.size(), actual.size());
		verify(this.mockReviewRepo, times(1)).findAll();
	}

	@Test
	void testFindByProductId_Success() {
		int id = this.dummyProduct.getId();
		List<Review> expected = new LinkedList<>();
		expected.add(this.dummyReview);

		given(this.pServ.findById(id)).willReturn(Optional.of(this.dummyProduct));
		given(this.mockReviewRepo.findByProduct(this.dummyProduct)).willReturn(expected);

		List<Review> actual = this.rServ.findByProductId(id);

		assertEquals(expected, actual);
		assertTrue(actual.containsAll(expected));
		verify(this.mockReviewRepo, times(1)).findByProduct(this.dummyProduct);
	}

	@Test
	void testFindByProductId_Failure_ProductIdNotFound() {
		int id = this.dummyProduct.getId();
		given(this.pServ.findById(id)).willReturn(Optional.empty());

		try {
			this.rServ.findByProductId(id);
			fail("Expected a ProductNotFoundException to be thrown");
		} catch (Exception e) {
			assertEquals("No product found with ID " + id, e.getMessage());
			verify(this.pServ, times(1)).findById(id);
		}
	}

	@Test
	void testFindByUserId_Success() {
		int id = this.dummyUser.getId();
		List<Review> expected = new LinkedList<>();
		expected.add(this.dummyReview);

		given(this.uServ.findById(id)).willReturn(Optional.of(this.dummyUser));
		given(this.mockReviewRepo.findByUser(this.dummyUser)).willReturn(expected);

		List<Review> actual = this.rServ.findByUserId(id);

		assertEquals(expected, actual);
		assertTrue(actual.containsAll(expected));
		verify(this.mockReviewRepo, times(1)).findByUser(this.dummyUser);
	}

	@Test
	void testFindByUserId_Failure_UserIdNotFound() {
		int id = this.dummyUser.getId();
		given(this.uServ.findById(id)).willReturn(Optional.empty());

		try {
			this.rServ.findByUserId(id);
			fail("Expected a UserNotFoundException to be thrown");
		} catch (Exception e) {
			assertEquals("No user found with ID " + id, e.getMessage());
			verify(this.uServ, times(1)).findById(id);
		}
	}

	@Test
	void testFindById() {
		int id = this.dummyReview.getId();
		given(this.mockReviewRepo.findById(id)).willReturn(Optional.of(this.dummyReview));

		Review expected = this.dummyReview;
		Review actual = this.rServ.findById(id);

		assertEquals(expected, actual);
		verify(this.mockReviewRepo, times(1)).findById(id);
	}

	@Test
	void testFindById_Failure_ReviewIdNotFound() {
		int id = 2;
		given(this.mockReviewRepo.findById(id)).willReturn(Optional.empty());

		try {
			this.rServ.findById(id);
			fail("Expected ReviewNotFoundException to be thrown");
		} catch (Exception e) {
			assertEquals(ReviewNotFoundException.class, e.getClass());
			verify(this.mockReviewRepo, times(1)).findById(id);
		}
	}

	@Test
	void testSave() {
		given(this.mockReviewRepo.save(this.dummyReview)).willReturn(this.dummyReview);

		Review expected = this.dummyReview;
		Review actual = this.rServ.save(this.dummyReview);

		assertEquals(expected, actual);
		verify(this.mockReviewRepo, times(1)).save(this.dummyReview);
	}

	@Test
	void testUpdate_Success() {
		int id = this.dummyReview.getId();
		User author = this.dummyReview.getUser();
		ReviewRequest updatedReview = new ReviewRequest(author.getId(), this.dummyProduct.getId(), 1, "Updated review",
				"Updated review body text");
		given(this.mockReviewRepo.findById(id)).willReturn(Optional.of(this.dummyReview));

		Review expected = new Review(id, updatedReview.getStars(), updatedReview.getTitle(), updatedReview.getReview(),
				null, null, this.dummyReview.getProduct(), author);
		given(this.mockReviewRepo.save(expected)).willReturn(expected);
		Review actual = this.rServ.update(updatedReview, id);

		assertEquals(expected, actual);
		verify(this.mockReviewRepo, times(1)).save(expected);
	}

	@Test
	void testUpdate_Failure_UnauthorizedUserId() {
		int id = this.dummyReview.getId();
		User author = this.dummyReview.getUser();
		int wrongId = author.getId() + 1;
		ReviewRequest updatedReview = new ReviewRequest(wrongId, this.dummyProduct.getId(), 3, "Updated review",
				"Updated review body text");
		given(this.mockReviewRepo.findById(id)).willReturn(Optional.of(this.dummyReview));

		try {
			this.rServ.update(updatedReview, id);
			fail("Expected a UnauthorizedReviewAccessException to be thrown");
		} catch (Exception e) {
			assertEquals(UnauthorizedReviewAccessException.class, e.getClass());
			verify(this.mockReviewRepo, times(1)).findById(id);
		}
	}

	@Test
	void testUpdate_Failure_ReviewIdNotFound() {
		int id = this.dummyReview.getId();
		int authorId = this.dummyReview.getUser().getId();
		ReviewRequest updatedReview = new ReviewRequest(authorId, this.dummyProduct.getId(), 1, "Updated review",
				"Updated review body text");
		given(this.mockReviewRepo.findById(id)).willReturn(Optional.empty());

		try {
			this.rServ.update(updatedReview, id);
			fail("Expected a ReviewNotFoundException to be thrown");
		} catch (Exception e) {
			assertEquals(ReviewNotFoundException.class, e.getClass());
			verify(this.mockReviewRepo, times(1)).findById(id);
		}
	}

	@Test
	void testDelete_Success() {
		int id = this.dummyReview.getId();
		given(this.mockReviewRepo.findById(id)).willReturn(Optional.of(this.dummyReview));

		this.rServ.delete(id, this.dummyReview.getUser().getId());
		verify(this.mockReviewRepo, times(1)).deleteById(id);
	}

	@Test
	void testDelete_Failure_UnauthorizedUserId() {
		int reviewId = this.dummyReview.getId();
		int userId = this.dummyReview.getUser().getId() + 1;
		given(this.mockReviewRepo.findById(reviewId)).willReturn(Optional.of(this.dummyReview));

		try {
			this.rServ.delete(reviewId, userId);
			fail("Expected a UnauthorizedReviewAccessException to be thrown");
		} catch (Exception e) {
			assertEquals(UnauthorizedReviewAccessException.class, e.getClass());
			verify(this.mockReviewRepo, times(1)).findById(reviewId);
		}
	}

	@Test
	void testDelete_Failure_ReviewIdNotFound() {
		int reviewId = this.dummyReview.getId();
		int authorId = this.dummyReview.getUser().getId();
		given(this.mockReviewRepo.findById(reviewId)).willReturn(Optional.empty());

		try {
			this.rServ.delete(reviewId, authorId);
			fail("Expected a ReviewNotFoundException with status code of 404");
		} catch (Exception e) {
			assertEquals(ReviewNotFoundException.class, e.getClass());
			verify(this.mockReviewRepo, times(1)).findById(reviewId);
		}
	}

}
