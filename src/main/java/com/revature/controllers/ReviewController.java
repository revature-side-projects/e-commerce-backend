package com.revature.controllers;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

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

import com.revature.annotations.Authorized;
import com.revature.dtos.ReviewRequest;
import com.revature.models.Product;
import com.revature.models.Review;
import com.revature.models.User;
import com.revature.services.ProductService;
import com.revature.services.ReviewService;

@RestController
@RequestMapping("/api/review")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class ReviewController {

	// TODO: Integrate review service
	private ReviewService reviewService;
	private ProductService productService;
	
	public ReviewController(ReviewService reviewService, ProductService productService) {
		super();
		this.reviewService = reviewService;
		this.productService = productService;
	}
	
	// Get All
	@GetMapping
	public ResponseEntity<List<Review>> getReviews() {
		return ResponseEntity.ok(null /* TODO: Call review service */);
	}
	
	// Get all reviews about a given product
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getReviewsOfProduct(@PathVariable("productId") int productId) {
		return ResponseEntity.ok(null /* TODO: Call review service */);
	}
	
	// Get all reviews written by a given user
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable("userId") int userId) {
		return ResponseEntity.ok(null /* TODO: Call review service */);
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
		// TODO: Call review service
		Optional<Product> optP = productService.findById(reviewRequest.getProductId());
		if(optP.isPresent()) {
			Review r = new Review(
					reviewRequest.getStars(), 
					reviewRequest.getTitle(), 
					reviewRequest.getReview(),
					new Timestamp(System.currentTimeMillis()),
					null,
					u,
					optP.get()
				);
			return ResponseEntity.ok(reviewService.save(r));
		} else {
			return null;
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
		// TODO: Call review service
		return null;
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
		// TODO: Call review service
		return null;
	}
}
