package com.revature.services;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import com.revature.dtos.ReviewRequest;
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
    	Optional<Product> optP = productService.findById(reviewRequest.getProductId());
		if(optP.isPresent()) {
			Review r = new Review(
					reviewRequest.getStars(), 
					reviewRequest.getTitle(), 
					reviewRequest.getReview(),
					new Timestamp(System.currentTimeMillis()),
					null,
					user,
					optP.get()
				);
			return reviewRepository.save(r);
		} else {
			throw new ResourceAccessException("No product found with ID " + reviewRequest.getProductId());
		}
    }
    
    public List<Review> findAll() {
    	return reviewRepository.findAll();
    }
    
    public List<Review> findByProductId(int productId) {
    	Optional<Product> optP = this.productService.findById(productId);
    	if(optP.isEmpty()) {
    		throw new ResourceAccessException("No product found with ID " + productId);
    	}
    	return reviewRepository.findByProduct(optP.get());
    }
    
    public List<Review> findByUserId(int userId) {
    	Optional<User> optU = this.userService.findById(userId);
    	if(optU.isEmpty()) {
    		throw new ResourceAccessException("No user found with ID " + userId);
    	}
    	return reviewRepository.findByUser(optU.get());
    }
    
    public Optional<Review> findById(int id) {
        return reviewRepository.findById(id);
    }
    
    public Review save(Review review) {
    	return reviewRepository.save(review);
    }
    
    public Review update(ReviewRequest reviewRequest, int id, int userId) {
    	Optional<Review> optR = reviewRepository.findById(id);
    	if(optR.isPresent()) {
    		Review review = optR.get();
        	if(review.getUser().getId() == userId) {
        		review.setStars(reviewRequest.getStars());
        		review.setTitle(reviewRequest.getTitle());
        		review.setReview(reviewRequest.getReview());
        		return reviewRepository.save(review);
        	} else {
        		throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        	}
    	} else {
    		throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
    	}
    }
    
    public Review delete(int id, int userId) {
    	Optional<Review> optR = reviewRepository.findById(id);
		if(optR.isPresent()) {
			Review r = optR.get();
			if(r.getUser().getId() == userId) {
				reviewRepository.deleteById(id);
				return r;
			} else {
				throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED); // User does not own this review
			}
		} else {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
    }
}
