package com.revature.exceptions;

public class AddressNotFoundException extends RuntimeException {

	public AddressNotFoundException() {

	}

	public AddressNotFoundException(String message) {
		super(message);
	}

	public AddressNotFoundException(int addressId) {
		super(String.format("No address found with ID %d", addressId));
	}

}
