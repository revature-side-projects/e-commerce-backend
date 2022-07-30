package com.revature.exceptions;

public class UserNotFoundException extends RuntimeException {

	public UserNotFoundException() {
		
	}
	
	public UserNotFoundException(String message) {
		super(message);
	}
	
	public UserNotFoundException(int userId) {
		super(String.format("No user found with ID %d", userId));
	}
}
