package com.revature.services;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.dtos.ReviewRequest;
import com.revature.exceptions.DuplicateReviewException;
import com.revature.exceptions.ProductNotFoundException;
import com.revature.exceptions.ReviewNotFoundException;
import com.revature.exceptions.UnauthorizedReviewAccessException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Product;
import com.revature.models.Review;
import com.revature.models.User;
import com.revature.repositories.ReviewRepository;

/**
 * ReviewService acts as a layer between our web controllers and our
 * ReviewRepository.
 * It handles basic logical processes to determine if the action can be
 * fulfilled, and
 * informs the controller how to respond.
 * 
 * @author CameronMSeibel
 * @author janderson38
 * @author JustDamion
 *
 */
@Service
public class ReviewService {

	private final ReviewRepository reviewRepository;
	private final ProductService productService;
	private final UserService userService;

	public ReviewService(
			ReviewRepository reviewRepository,
			ProductService productService,
			UserService userService) {
		this.reviewRepository = reviewRepository;
		this.productService = productService;
		this.userService = userService;
	}

	/**
	 * Create a new review authored by the given user
	 * 
	 * @param reviewRequest containing relevant review information
	 * @param user          who authored the review
	 * @return the new review object
	 */
	public Review add(ReviewRequest reviewRequest) {
		Optional<Product> optionalProduct = productService.findById(reviewRequest.getProductId());
		if (optionalProduct.isPresent()) {
			Optional<User> user = userService.findById(reviewRequest.getUserId());
			if (!user.isPresent()) {
				throw new UserNotFoundException();
			}
			
			Review review = new Review(
					reviewRequest.getStars(),
					reviewRequest.getTitle(),
					reviewRequest.getReview(),
					user.get(),
					optionalProduct.get());
			Product product = optionalProduct.get();
			Optional<Review> optionalReview = this.findByProductId(product.getId()).stream()
					.filter(r -> r.getUser().getId() == reviewRequest.getUserId())
					.findFirst();
			if (optionalReview.isPresent()) {
				throw new DuplicateReviewException("You have already written a review for this product.");
			}
			review.setPosted(Timestamp.valueOf(LocalDateTime.now()));
			review.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
			review.setUser(user.get());
			review = reviewRepository.save(review);

			return review;
		} else {
			throw new ProductNotFoundException(
					String.format("No product found with ID %d", reviewRequest.getProductId()));
		}
	}

	/**
	 * Retrieve a list of all reviews
	 * 
	 * @return a list of all reviews
	 */
	public List<Review> findAll() {
		return reviewRepository.findAll();
	}

	/**
	 * Retrieve a list of reviews associated with a given product ID
	 * 
	 * @param productId an int corresponding to the ID of a product
	 * @return a list of reviews about the product
	 * @throws ProductNotFoundException when productId does not map to a product
	 */
	public List<Review> findByProductId(int productId) {
		Optional<Product> optionalProduct = this.productService.findById(productId);
		if (!optionalProduct.isPresent()) {
			throw new ProductNotFoundException(String.format("No product found with ID %d", productId));
		}

		return reviewRepository.findByProduct(optionalProduct.get());
	}

	/**
	 * Retrive a list of reviews authored by a given user
	 * 
	 * @param userId ID corresponding to the user whose reviews are to be retrieved
	 * @return a list of reviews
	 * @throws UserNotFoundException when userId does not map to a user
	 */
	public List<Review> findByUserId(int userId) {
		Optional<User> optionalUser = this.userService.findById(userId);
		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException(String.format("No user found with ID %d", userId));
		}

		return reviewRepository.findByUser(optionalUser.get());
	}

	/**
	 * Find a single review by its ID
	 * 
	 * @param id of the review
	 * @return a single Review object
	 * @throws ReviewNotFoundException if the ID does not map to a review
	 */
	public Review findById(int id) {
		Optional<Review> optionalReview = reviewRepository.findById(id);
		if (!optionalReview.isPresent()) {
			throw new ReviewNotFoundException(String.format("No review found with ID %d", id));
		} else {
			return optionalReview.get();
		}
	}

	/**
	 * Save a review, creating a new review or updating an old review
	 * 
	 * @param review to be saved
	 * @return the saved version of the review
	 */
	public Review save(Review review) {
		return reviewRepository.save(review);
	}

	/**
	 * Update a review with a given ID so long as the review was authored by the
	 * user with the user ID supplied
	 * 
	 * @param reviewRequest to update the current review with
	 * @param id            of the review to update
	 * @param userId        of the user making the update request
	 * @return the updated version of the review
	 * @throws ReviewNotFoundException           if the review ID does not map to a
	 *                                           review
	 * @throws UnauthorizedReviewAccessException if the user making the request did
	 *                                           not author the review
	 */
	public Review update(ReviewRequest reviewRequest, int id) {
		Optional<Review> optionalReview = reviewRepository.findById(id);
		if (optionalReview.isPresent()) {
			Review review = optionalReview.get();
			if (review.getUser().getId() == reviewRequest.getUserId()) {
				review.setStars(reviewRequest.getStars());
				review.setTitle(reviewRequest.getTitle());
				review.setReview(reviewRequest.getReview());
				review.setUpdated(Timestamp.valueOf(LocalDateTime.now()));
				return reviewRepository.save(review);
			} else {
				throw new UnauthorizedReviewAccessException("You are not authorized to modify this review.");
			}
		} else {
			throw new ReviewNotFoundException(String.format("No review found with ID %d", id));
		}
	}

	/**
	 * Delete a review with the given ID so long as the user making the delete
	 * request authored the review
	 * 
	 * @param id     of the review to be deleted
	 * @param userId of the user making the delete request
	 * @return a copy of the now deleted review, that you may look back on your fond
	 *         memories of this review.
	 * @throws ReviewNotFoundException           if the ID does not map to a review
	 * @throws UnauthorizedReviewAccessException if the user making the request does
	 *                                           not own the review
	 */
	public Review delete(int id, int userId) {
		Optional<Review> optionalReview = reviewRepository.findById(id);
		if (optionalReview.isPresent()) {
			Review r = optionalReview.get();
			if (r.getUser().getId() == userId) {
				reviewRepository.deleteById(id);
				return r;
			} else {
				throw new UnauthorizedReviewAccessException("You are not authorized to delete this review."); // User
																												// does
																												// not
																												// own
																												// this
																												// review
			}
		} else {
			throw new ReviewNotFoundException(String.format("No review found with ID %d", id));
		}
	}
}
