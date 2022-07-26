package com.revature.exceptions;

public class ReviewNotFoundException extends RuntimeException {

	public ReviewNotFoundException() {
		
	}
	
	public ReviewNotFoundException(String message) {
		super(message);
	}
}
