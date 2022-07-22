package com.revature.controllers;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.revature.annotations.Authorized;
import com.revature.dtos.ReviewRequest;
import com.revature.models.Review;
import com.revature.models.User;
import com.revature.services.ReviewService;

@RestController
@RequestMapping("/api/review")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class ReviewController {

	// TODO: Integrate review service
	private ReviewService reviewService;
	
	public ReviewController(ReviewService reviewService) {
		super();
		this.reviewService = reviewService;
	}
	
	// Get All
	@GetMapping
	public ResponseEntity<List<Review>> getReviews() {
		return ResponseEntity.ok(reviewService.findAll());
	}
	
	// Get all reviews about a given product
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getReviewsOfProduct(@PathVariable("productId") int productId) {
		try {
			return ResponseEntity.ok(reviewService.findByProductId(productId));
		} catch(ResourceAccessException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(null);
		}
	}
	
	// Get all reviews written by a given user
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable("userId") int userId) {
		try {
			return ResponseEntity.ok(reviewService.findByUserId(userId));
		} catch(ResourceAccessException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(null);
		}
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Review> getReviewById(@PathVariable("id") int id) {
		Optional<Review> optR = reviewService.findById(id);
		if(optR.isPresent()) {
			return ResponseEntity.ok(optR.get());
		} else {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(null);
		}
	}
	
	/**
	 * Create a new review tied to the logged in User
	 * @param reviewRequest
	 * @param session
	 * @return
	 */
	@Authorized
	@PostMapping
	public ResponseEntity<Review> addReview(@RequestBody ReviewRequest reviewRequest, HttpSession session) {
		User u = (User) session.getAttribute("user"); // May need to try catch - but this shouldn't execute if 
													  // "user" session attribute is null anyway
		try {
			return ResponseEntity.ok(reviewService.add(reviewRequest, u));
		} catch(ResourceAccessException e) {
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(null);
		}
	}
	
	/**
	 * Update a review with the given request's information, so long as session user owns the review
	 * @param reviewRequest
	 * @param id ID of review to update
	 * @param session
	 * @return
	 */
	@Authorized
	@PutMapping("/{id}")
	public ResponseEntity<Review> updateReview(@RequestBody ReviewRequest reviewRequest, @PathVariable("id") int id, HttpSession session) {
		int userId = ((User) session.getAttribute("user")).getId();
		try {
		 	return ResponseEntity.ok(reviewService.update(reviewRequest, id, userId));
		} catch(HttpClientErrorException e) {
			return ResponseEntity
					.status(e.getStatusCode())
					.body(null);
		}
	}
	
	/**
	 * Delete a review given a review's ID, and that the user requesting the delete owns the review
	 * @param id ID of Review to delete
	 * @param session Current HTTP session
	 * @return
	 */
	@Authorized
	@DeleteMapping("/{id}")
	public ResponseEntity<Review> deleteReview(@PathVariable("id") int id, HttpSession session) {
		int userId = ((User) session.getAttribute("user")).getId();
		try {
			return ResponseEntity.ok(reviewService.delete(id, userId));
		} catch(HttpClientErrorException e) {
			return ResponseEntity
					.status(e.getStatusCode())
					.body(null);
		}
	}
}
