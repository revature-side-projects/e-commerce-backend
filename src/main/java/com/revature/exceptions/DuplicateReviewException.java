package com.revature.exceptions;

public class DuplicateReviewException extends RuntimeException {

	public DuplicateReviewException() {
		
	}
	
	public DuplicateReviewException(String message) {
		super(message);
	}
}
