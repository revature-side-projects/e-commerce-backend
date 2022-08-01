package com.revature.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Defines unit tests for various custom exceptions found in the {@link com.revature.exceptions} package.
 * 
 * @author CameronMSeibel
 *
 */
@ExtendWith(MockitoExtension.class)
public class ExceptionsTest {
	
	@Test
	void testAddressNotFound_Success_NoArgs() {
		try {
			throw new AddressNotFoundException();
		} catch (Exception e) {
			assertEquals(AddressNotFoundException.class, e.getClass());
		}
	}
	
	@Test
	void testAddressNotFound_Success_WithMessage() {
		String message = "No such address found";
		try {
			throw new AddressNotFoundException(message);
		} catch (Exception e) {
			assertEquals(AddressNotFoundException.class, e.getClass());
			assertEquals(message, e.getMessage());
		}
	}
	
	@Test
	void testAddressNotFound_Success_WithID() {
		String message = "No address found with ID %d";
		int id = 404;
		try {
			throw new AddressNotFoundException(id);
		} catch (Exception e) {
			assertEquals(AddressNotFoundException.class, e.getClass());
			assertEquals(String.format(message, id), e.getMessage());
		}
	}
	
	@Test
	void testDuplicateReviewException_Success() {
		try {
			throw new DuplicateReviewException();
		} catch (Exception e) {
			assertEquals(DuplicateReviewException.class, e.getClass());
		}
	}
	
	@Test
	void testDuplicateReviewException_Success_WithMessage() {
		String message = "You have already written a review for this product";
		try {
			throw new DuplicateReviewException(message);
		} catch (Exception e) {
			assertEquals(DuplicateReviewException.class, e.getClass());
			assertEquals(message, e.getMessage());
		}
	}
	
	@Test
	void testFileUploadException_Success() {
		try {
			throw new FileUploadException();
		} catch (Exception e) {
			assertEquals(FileUploadException.class, e.getClass());
		}
	}
	
	@Test
	void testFileUploadException_Success_WithMessage() {
		String message = "File upload failed";
		try {
			throw new FileUploadException(message);
		} catch (Exception e) {
			assertEquals(FileUploadException.class, e.getClass());
			assertEquals(message, e.getMessage());
		}
	}
	
	@Test
	void testInvalidProductInputException_Success() {
		try {
			throw new InvalidProductInputException();
		} catch (Exception e) {
			assertEquals(InvalidProductInputException.class, e.getClass());
		}
	}
	
	@Test
	void testInvalidProductInputException_Success_WithMessage() {
		String message = "Inavlid product format";
		try {
			throw new InvalidProductInputException(message);
		} catch (Exception e) {
			assertEquals(InvalidProductInputException.class, e.getClass());
			assertEquals(message, e.getMessage());
		}
	}
	
	@Test
	void testInvalidRoleException_Success() {
		try {
			throw new InvalidRoleException();
		} catch (Exception e) {
			assertEquals(InvalidRoleException.class, e.getClass());
		}
	}
	
	@Test
	void testInvalidRoleException_Success_WithMessage() {
		try {
			throw new InvalidRoleException();
		} catch (Exception e) {
			assertEquals(InvalidRoleException.class, e.getClass());
		}
	}
	
	@Test 
	void testMultipartFileConversionException_Success() {
		try {
			throw new MultipartFileConversionException();
		} catch (Exception e) {
			assertEquals(MultipartFileConversionException.class, e.getClass());
		}
	}
	
	@Test
	void testMultipartFileConversionException_Success_WithMessage() {
		String message = "Failed to make conversion";
		try {
			throw new MultipartFileConversionException(message);
		} catch (Exception e) {
			assertEquals(MultipartFileConversionException.class, e.getClass());
			assertEquals(message, e.getMessage());
		}
	}
	
	@Test
	void testNotLoggedInException() {
		try {
			throw new NotLoggedInException();
		} catch (Exception e) {
			assertEquals(NotLoggedInException.class, e.getClass());
		}
	}
	
	@Test
	void testNotLoggedInException_WithMessage() {
		String message = "You must be logged in to access this resource";
		try {
			throw new NotLoggedInException(message);
		} catch (Exception e) {
			assertEquals(NotLoggedInException.class, e.getClass());
			assertEquals(message, e.getMessage());
		}
	}
	
	@Test
	void testNotLoggedInException_WithMessageAndThrowable() {
		String message = "You must be logged in to access this resource";
		try {
			throw new NotLoggedInException(message, new Throwable());
		} catch (Exception e) {
			assertEquals(NotLoggedInException.class, e.getClass());
			assertEquals(message, e.getMessage());
		}
	}
	
	@Test
	void testNotLoggedInException_WithMessageAndThrowableAndFlags() {
		String message = "You must be logged in to access this resource";
		try {
			throw new NotLoggedInException(message, new Throwable(), true, false);
		} catch (Exception e) {
			assertEquals(NotLoggedInException.class, e.getClass());
			assertEquals(message, e.getMessage());
		}
	}
	
	@Test 
	void testProductNotFoundException() {
		try {
			throw new ProductNotFoundException();
		} catch (Exception e) {
			assertEquals(ProductNotFoundException.class, e.getClass());
		}
	}
	
	@Test 
	void testProductNotFoundException_WithMessage() {
		String message = "Product not found";
		try {
			throw new ProductNotFoundException(message);
		} catch (Exception e) {
			assertEquals(ProductNotFoundException.class, e.getClass());
			assertEquals(message, e.getMessage());
		}
	}
	
	@Test 
	void testProductNotFoundException_WithID() {
		String message = "No product found with ID %d";
		int id = 404;
		try {
			throw new ProductNotFoundException(id);
		} catch (Exception e) {
			assertEquals(ProductNotFoundException.class, e.getClass());
			assertEquals(String.format(message, id), e.getMessage());
		}
	}
	
	@Test
	void testReviewNotFoundException() {
		try {
			throw new ReviewNotFoundException();
		} catch (Exception e) {
			assertEquals(ReviewNotFoundException.class, e.getClass());
		}
	}
	
	@Test
	void testReviewNotFoundException_WithMessage() {
		String message = "Review not found";
		try {
			throw new ReviewNotFoundException(message);
		} catch (Exception e) {
			assertEquals(ReviewNotFoundException.class, e.getClass());
			assertEquals(message, e.getMessage());
		}
	}
	
	@Test
	void testReviewNotFoundException_WithID() {
		String message = "No review found with ID %d";
		int id = 404;
		try {
			throw new ReviewNotFoundException(id);
		} catch (Exception e) {
			assertEquals(ReviewNotFoundException.class, e.getClass());
			assertEquals(String.format(message, id), e.getMessage());
		}
	}
	
	@Test 
	void testUnauthorizedReviewAccessException() {
		try {
			throw new UnauthorizedReviewAccessException();
		} catch (Exception e) {
			assertEquals(UnauthorizedReviewAccessException.class, e.getClass());
		}
	}
	
	@Test
	void testUnauthorizedReviewAccessException_WithMessage() {
		String message = "You are not authorized to make changes to this review.";
		try {
			throw new UnauthorizedReviewAccessException(message);
		} catch (Exception e) {
			assertEquals(UnauthorizedReviewAccessException.class, e.getClass());
			assertEquals(message, e.getMessage());
		}
	}
	
	@Test
	void testUserNotFoundException_Success() {
		try {
			throw new UserNotFoundException();
		} catch (Exception e) {
			assertEquals(UserNotFoundException.class, e.getClass());
		}
	}
	
	@Test
	void testUserNotFoundException_Success_WithMessage() {
		String message = "User not found";
		try {
			throw new UserNotFoundException(message);
		} catch (Exception e) {
			assertEquals(UserNotFoundException.class, e.getClass());
			assertEquals(message, e.getMessage());
		}
	}
	
	@Test
	void testUserNotFoundException_Success_WithID() {
		String message = "No user found with ID %d";
		int id = 404;
		try {
			throw new UserNotFoundException(id);
		} catch (Exception e) {
			assertEquals(UserNotFoundException.class, e.getClass());
			assertEquals(String.format(message, id), e.getMessage());
		}
	}
}
