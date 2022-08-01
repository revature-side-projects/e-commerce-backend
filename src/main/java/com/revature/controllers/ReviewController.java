package com.revature.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.ReviewRequest;
import com.revature.models.Review;
import com.revature.services.ReviewService;

/**
 * This controller handles requests for the "/api/review" endpoint
 * 
 * @author CameronMSeibel
 * @author janderson38
 * @author JustDamion
 *
 */
@RestController
@RequestMapping("/api/review")
@CrossOrigin(origins = "*")
public class ReviewController {

	private ReviewService reviewService;

	public ReviewController(ReviewService reviewService) {
		super();
		this.reviewService = reviewService;
	}

	/**
	 * Get all Reviews
	 * @return Http response with array of all Reviews
	 */
	@GetMapping
	public ResponseEntity<List<Review>> getReviews() {
		return ResponseEntity.ok(reviewService.findAll());
	}

	/**
	 * Get all reviews by productId
	 * @param productId
	 * @return Http response with array of Reviews belonging to a Product
	 */
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getReviewsOfProduct(@PathVariable("productId") int productId) {
		return ResponseEntity.ok(reviewService.findByProductId(productId));
	}

	/**
	 * Get all reviews written by a given user
	 * @param userId User Id
	 * @return Http response with array of Reviews belonging to a User
	 */
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable("userId") int userId) {
		return ResponseEntity.ok(reviewService.findByUserId(userId));
	}

	/**
	 * Get a review by its ID
	 * @param id Review Id
	 * @return Review
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Review> getReviewById(@PathVariable("id") int id) {
		Review review = reviewService.findById(id);
		return ResponseEntity.ok(review);
	}

	/**
	 * Create a new review tied to the logged in User
	 * 
	 * @param reviewRequest the requested Review Object
	 * @return an "ok" ResponseEntity so long as the product being reviewed exists
	 */
	@PostMapping
	public ResponseEntity<Review> addReview(@RequestBody ReviewRequest reviewRequest) {
		return ResponseEntity.ok(reviewService.add(reviewRequest));
	}

	/**
	 * Update a review with the given request's information, so long as session user
	 * owns the review
	 *
	 * @param reviewRequest the requested Review Object
	 * @param id            ID of review to update
	 * @return an "ok" ResponseEntity so long as the current user authored the
	 *         review, and the review exists
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Review> updateReview(@RequestBody ReviewRequest reviewRequest, @PathVariable("id") int id) {
		return ResponseEntity.ok(reviewService.update(reviewRequest, id));
	}

	/**
	 * Delete a review given a review's ID, and that the user requesting the delete
	 * owns the review
	 * 
	 * @param id      ID of Review to delete
	 * @return an "ok" ResponseEntity so long as the current user authored the
	 *         review, and the review exists
	 */
	@DeleteMapping("/{userId}/{id}")
	public ResponseEntity<Review> deleteReview(@PathVariable("userId") int userId, @PathVariable("id") int id) {
		return ResponseEntity.ok(reviewService.delete(id, userId));
	}
}
