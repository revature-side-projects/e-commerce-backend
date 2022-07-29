package com.revature.repositories;

import com.revature.models.Product;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
	
	@Query("From Product WHERE lower(Name) LIKE lower(concat('%', ?1,'%'))")
	List<Product> findByNameContains(String pattern);
	
	@Query("From Product WHERE PRICE BETWEEN ?1 and ?2")
	List<Product> findByPriceRange(double minPrice, double maxPrice);
	
	@Query(value="select pd.* from products as pd inner join reviews as rw on pd.product_id = rw.product_id group by pd.product_id order by avg(rw.stars) desc",nativeQuery = true)
	List<Product> filterByRating();
}