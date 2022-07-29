package com.revature.advice;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.revature.exceptions.DuplicateReviewException;
import com.revature.exceptions.InvalidProductInputException;
import com.revature.exceptions.InvalidRoleException;
import com.revature.exceptions.NotLoggedInException;
import com.revature.exceptions.ProductNotFoundException;
import com.revature.exceptions.ReviewNotFoundException;
import com.revature.exceptions.UnauthorizedReviewAccessException;
import com.revature.exceptions.UserNotFoundException;

@RestControllerAdvice
public class RestExceptionHandler {

	@ExceptionHandler(NotLoggedInException.class)
	public ResponseEntity<Object> handleNotLoggedInException(HttpServletRequest request,
			NotLoggedInException notLoggedInException) {

		String errorMessage = "Must be logged in to perform this action";
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
	}

	@ExceptionHandler(InvalidRoleException.class)
	public ResponseEntity<Object> handleInvalidRoleException(HttpServletRequest request,
			InvalidRoleException invalidRoleException) {
		String errorMessage = "Missing Admin role to perform this action";
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);

	}

	@ExceptionHandler(InvalidProductInputException.class)
	public ResponseEntity<Object> handleInvalidProductInputException(HttpServletRequest request,
			InvalidProductInputException invalidProductInputException) {
		String errorMessage = "Missing input is not allowed";
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);

	}

	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Object> handleProductNotFoundException(HttpServletRequest request,
			ProductNotFoundException productNotFoundException) {

		String errorMessage = productNotFoundException.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(ReviewNotFoundException.class)
	public ResponseEntity<Object> handleReviewNotFoundException(HttpServletRequest request,
			ReviewNotFoundException reviewNotFoundException) {
		System.out.println("Review not found!");
		String errorMessage = reviewNotFoundException.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<Object> handleReviewNotFoundException(HttpServletRequest request,
			UserNotFoundException userNotFoundException) {

		String errorMessage = userNotFoundException.getMessage();
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
	}

	@ExceptionHandler(DuplicateReviewException.class)
	public ResponseEntity<Object> handleReviewNotFoundException(HttpServletRequest request,
			DuplicateReviewException duplicateReviewException) {

		String errorMessage = duplicateReviewException.getMessage();
		return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
	}

	@ExceptionHandler(UnauthorizedReviewAccessException.class)
    public ResponseEntity<Object> handleReviewNotFoundException(
		HttpServletRequest request, 
		UnauthorizedReviewAccessException unauthorizedReviewAccessException
	) {
    	
    	String errorMessage = unauthorizedReviewAccessException.getMessage();
    	return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
    }
}
