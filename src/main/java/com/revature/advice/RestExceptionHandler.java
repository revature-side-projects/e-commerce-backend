package com.revature.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.revature.exceptions.InvalidProductInputException;
import com.revature.exceptions.InvalidRoleException;
import com.revature.exceptions.NotLoggedInException;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(NotLoggedInException.class)
    public ResponseEntity<Object> handleNotLoggedInException(HttpServletRequest request, NotLoggedInException notLoggedInException) {

        String errorMessage = "Must be logged in to perform this action";

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }
    
     @ExceptionHandler(InvalidRoleException.class)
     public ResponseEntity<Object> handleInvalidRoleException(HttpServletRequest request, InvalidRoleException invalidRoleException) {
    	 String errorMessage = "Missing Admin role to perform this action";
    	 return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    	 
     }
     @ExceptionHandler(InvalidProductInputException.class)
     public ResponseEntity<Object> handleInvalidProductInputException(HttpServletRequest request, InvalidProductInputException invalidProductInputException) {
    	 String errorMessage = "Missing input is not allowed";
    	 return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    	 
     }
     
     
    
}
