package com.revature.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.models.Review;
import com.revature.repositories.ReviewRepository;

@Service
public class ReviewService {
	
	private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    
    public Optional<Review> findById(int id) {
        return reviewRepository.findById(id);
    }
    
    public Review save(Review review) {
    	return reviewRepository.save(review);
    }
    
    public void delete(int id) {
        reviewRepository.deleteById(id);
    }
}
