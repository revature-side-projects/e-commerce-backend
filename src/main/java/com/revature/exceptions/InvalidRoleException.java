package com.revature.exceptions;

//Exception to be thrown by the AuthAspect
//Will be handled by a Spring Exception Handler to return a 403
public class InvalidRoleException extends RuntimeException{
	
    public InvalidRoleException() {
    }

    public InvalidRoleException(String message) {
        super(message);
    }

    public InvalidRoleException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRoleException(Throwable cause) {
        super(cause);
    }

    public InvalidRoleException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
