package com.revature.exceptions;

public class UnauthorizedReviewAccessException extends RuntimeException {

	public UnauthorizedReviewAccessException() {
		
	}
	
	public UnauthorizedReviewAccessException(String message) {
		super(message);
	}
}
