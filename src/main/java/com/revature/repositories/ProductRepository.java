package com.revature.repositories;

import com.revature.models.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query("From Product WHERE lower(Name) LIKE lower(concat('%', ?1,'%'))")
	List<Product> findByNameContains(String pattern);
	
	@Query("From Product WHERE PRICE BETWEEN ?1 and ?2")
	List<Product> findByPriceRange(double minPrice, double maxPrice);
	

}