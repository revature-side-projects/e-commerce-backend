package com.revature.exceptions;

public class ProductNotFoundException extends RuntimeException {
	
	public ProductNotFoundException() {
		
	}
	
	public ProductNotFoundException(String message) {
		super(message);
	}
	
	public ProductNotFoundException(int productId) {
		super(String.format("No product found with ID %d", productId));
	}
	
}
