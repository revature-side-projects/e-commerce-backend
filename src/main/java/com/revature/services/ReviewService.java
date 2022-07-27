package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.revature.dtos.ReviewRequest;
import com.revature.exceptions.DuplicateReviewException;
import com.revature.exceptions.ProductNotFoundException;
import com.revature.exceptions.ReviewNotFoundException;
import com.revature.exceptions.UnauthorizedReviewAccessException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Product;
import com.revature.models.Review;
import com.revature.models.User;
import com.revature.repositories.ReviewRepository;

@Service
public class ReviewService {
	
	Logger logger = LoggerFactory.getLogger(ReviewService.class);
	
	private final ReviewRepository reviewRepository;
	private final ProductService productService;
	private final UserService userService;

    public ReviewService(
    		ReviewRepository reviewRepository,
    		ProductService productService,
    		UserService userService
		) {
        this.reviewRepository = reviewRepository;
        this.productService = productService;
        this.userService = userService;
    }
    
    public Review add(ReviewRequest reviewRequest, User user) {
    	Optional<Product> optionalProduct = productService.findById(reviewRequest.getProductId());
		if(optionalProduct.isPresent()) {
			Review review = new Review(
					reviewRequest.getStars(), 
					reviewRequest.getTitle(), 
					reviewRequest.getReview(),
					user,
					optionalProduct.get()
				);
			Product product = optionalProduct.get();
			Optional<Review> optionalReview = this.findByProductId(product.getId()).stream()
						.filter(r -> r.getUser().getId() == user.getId())
						.findFirst();
			if(optionalReview.isPresent()) {
				throw new DuplicateReviewException("You have already written a review for this product.");
			}
			review = reviewRepository.save(review);
		
			logger.info(String.format("Review with ID: %d successfully submitted", review.getId()));
			return review;
		} else {
			logger.warn(String.format("No product found with ID %d", reviewRequest.getProductId()));
			throw new ProductNotFoundException(String.format("No product found with ID %d", reviewRequest.getProductId()));
		}
    }
    
    public List<Review> findAll() {
    	return reviewRepository.findAll();
    }
    
    public List<Review> findByProductId(int productId) {
    	Optional<Product> optionalProduct = this.productService.findById(productId);
    	if(!optionalProduct.isPresent()) {
    		logger.warn(String.format("No product found with ID %d", productId));
    		throw new ProductNotFoundException(String.format("No product found with ID %d", productId));
    	}
    	
    	logger.info(String.format("Product with ID: %d successfully found", optionalProduct.get().getId()));
    	return reviewRepository.findByProduct(optionalProduct.get());
    }
    
    public List<Review> findByUserId(int userId) {
    	Optional<User> optionalUser = this.userService.findById(userId);
    	if(!optionalUser.isPresent()) {
    		logger.warn(String.format("No user found with ID %d", userId));
    		throw new UserNotFoundException(String.format("No user found with ID %d", userId));
    	}
    	
    	logger.info(String.format("User with ID: %d successfully found", optionalUser.get().getId()));
    	return reviewRepository.findByUser(optionalUser.get());
    }
    
    public Review findById(int id) {
    	Optional<Review> optionalReview = reviewRepository.findById(id);
    	if(!optionalReview.isPresent()) {
    		logger.warn(String.format("No review found with ID %d", id));
    		throw new ReviewNotFoundException(String.format("No review found with ID %d", id));
    	}
    	logger.info(String.format("Review with ID: %d successfully found", optionalReview.get().getId()));
        return optionalReview.get();
    }
    
    public Review save(Review review) {
    	return reviewRepository.save(review);
    }
    
    public Review update(ReviewRequest reviewRequest, int id, int userId) {
    	Optional<Review> optionalReview = reviewRepository.findById(id);
    	if(optionalReview.isPresent()) {
    		Review review = optionalReview.get();
        	if(review.getUser().getId() == userId) {
        		review.setStars(reviewRequest.getStars());
        		review.setTitle(reviewRequest.getTitle());
        		review.setReview(reviewRequest.getReview());
        		logger.info(String.format("Review with ID: %d successfully updated", optionalReview.get().getId()));
        		return reviewRepository.save(review);
        	} else {
        		logger.warn("You are not authorized to modify this review.");
        		throw new UnauthorizedReviewAccessException("You are not authorized to modify this review.");
        	}
    	} else {
    		logger.warn(String.format("No review found with ID %d", id));
    		throw new ReviewNotFoundException(String.format("No review found with ID %d", id));
    	}
    }
    
    public Review delete(int id, int userId) {
    	Optional<Review> optionalReview = reviewRepository.findById(id);
		if(optionalReview.isPresent()) {
			Review r = optionalReview.get();
			if(r.getUser().getId() == userId) {
				reviewRepository.deleteById(id);
				logger.info("Review with ID: %d successfully deleted", optionalReview.get().getId());
				return r;
			} else {
				logger.warn("You are not authorized to delete this review.");
				throw new UnauthorizedReviewAccessException("You are not authorized to delete this review."); // User does not own this review
			}
		} else {
			logger.warn(String.format("No review found with ID %d", id));
			throw new ReviewNotFoundException(String.format("No review found with ID %d", id));
		}
    }
}
