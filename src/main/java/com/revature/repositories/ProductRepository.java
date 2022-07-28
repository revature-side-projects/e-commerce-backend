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
	
	
//	select pd.product_id, pd.description, pd.image, pd.name, pd.price, pd.quantity,avg(rw.stars) 
//	from products as pd inner join reviews as rw
//	on pd.PRODUCT_ID = rw.product_id order by avg(rw.stars) desc;
//	Select pd.name From Product pd inner join Review rw on pd.PRODUCT_ID = rw.product_id order by rw.stars desc
//	@Query("Select pd From Product pd inner join Review rw where pd.id = rw.id order by avg(rw.stars) desc")
	@Query(value="select pd.* from products as pd inner join reviews as rw on pd.product_id = rw.product_id group by pd.product_id order by avg(rw.stars) desc",nativeQuery = true)
	List<Product> filterByRating();
}