package com.revature.exceptions;

public class ReviewNotFoundException extends RuntimeException {

	public ReviewNotFoundException() {
		
	}
	
	public ReviewNotFoundException(String message) {
		super(message);
	}
	
	public ReviewNotFoundException(int reviewId) {
		super(String.format("No review found with ID %d", reviewId));
	}
}
