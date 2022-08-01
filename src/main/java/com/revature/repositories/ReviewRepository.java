package com.revature.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Product;
import com.revature.models.Review;
import com.revature.models.User;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

	List<Review> findByProduct(Product p);
	List<Review> findByUser(User u);
}
