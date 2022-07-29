package com.revature.services;

import java.util.List;
import java.util.Optional;

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
		
			return review;
		} else {
			throw new ProductNotFoundException(String.format("No product found with ID %d", reviewRequest.getProductId()));
		}
    }
    
    public List<Review> findAll() {
    	return reviewRepository.findAll();
    }
    
    public List<Review> findByProductId(int productId) {
    	Optional<Product> optionalProduct = this.productService.findById(productId);
    	if(!optionalProduct.isPresent()) {
    		throw new ProductNotFoundException(String.format("No product found with ID %d", productId));
    	}
    	
    	return reviewRepository.findByProduct(optionalProduct.get());
    }
    
    public List<Review> findByUserId(int userId) {
    	Optional<User> optionalUser = this.userService.findById(userId);
    	if(!optionalUser.isPresent()) {
    		throw new UserNotFoundException(String.format("No user found with ID %d", userId));
    	}
    	
    	return reviewRepository.findByUser(optionalUser.get());
    }
    
    public Review findById(int id) {
    	Optional<Review> optionalReview = reviewRepository.findById(id);
    	if(!optionalReview.isPresent()) {
    		throw new ReviewNotFoundException(String.format("No review found with ID %d", id));
    	} else {
            return optionalReview.get();
    	}
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
        		return reviewRepository.save(review);
        	} else {
        		throw new UnauthorizedReviewAccessException("You are not authorized to modify this review.");
        	}
    	} else {
    		throw new ReviewNotFoundException(String.format("No review found with ID %d", id));
    	}
    }
    
    public Review delete(int id, int userId) {
    	Optional<Review> optionalReview = reviewRepository.findById(id);
		if(optionalReview.isPresent()) {
			Review r = optionalReview.get();
			if(r.getUser().getId() == userId) {
				reviewRepository.deleteById(id);
				return r;
			} else {
				throw new UnauthorizedReviewAccessException("You are not authorized to delete this review."); // User does not own this review
			}
		} else {
			throw new ReviewNotFoundException(String.format("No review found with ID %d", id));
		}
    }
}
