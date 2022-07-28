package com.revature.exceptions;

public class InvalidProductInputException extends RuntimeException {
	
    public InvalidProductInputException() {
    }

    public InvalidProductInputException(String message) {
        super(message);
    }

    public InvalidProductInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidProductInputException(Throwable cause) {
        super(cause);
    }

    public InvalidProductInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
