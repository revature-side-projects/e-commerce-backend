package com.revature.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class ReviewController {

	// TODO: Integrate review controller
	
	public ReviewController() {
		// TODO: Integrate review controller
	}
	
	@GetMapping
	public ResponseEntity<List<Review>> getReviews() {
		return ResponseEntity.ok(/* TODO: Call review controller */);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getReviewsOfProduct(@PathVariable("productId") int productId) {
		return ResponseEntity.ok(/* TODO: Call review controller */);
	}
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable("userId") int userId) {
		return ResponseEntity.ok(/* TODO: Call review controller */);
	}
}
