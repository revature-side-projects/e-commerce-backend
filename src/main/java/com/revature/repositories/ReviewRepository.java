package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Review;

public interface ReviewRepository extends JpaRepository<Review, Integer> {

}
