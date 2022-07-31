package com.revature.exceptions;

public class FileUploadException extends RuntimeException {
	
	public FileUploadException() {

	}

	public FileUploadException(String message) {
		super(message);
	}
}
