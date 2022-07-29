package com.revature.services;

import com.revature.dtos.ProductInfo;
import com.revature.exceptions.ProductNotFoundException;
import com.revature.models.Product;
import com.revature.repositories.ProductRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
	
	Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    public Optional<Product> findById(int id) {
    	Optional<Product> optionalProduct = productRepository.findById(id);
    	if(!optionalProduct.isPresent()) {
    		logger.warn(String.format("No product found with ID %d", id));
    		throw new ProductNotFoundException(String.format("No product found with ID %d", id));
    	}
    	logger.info(String.format("Product with ID: %d successfully found", optionalProduct.get().getId()));
        return productRepository.findById(id);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }
    
    public List<Product> saveAll(List<Product> productList, List<ProductInfo> metadata) {
    	return productRepository.saveAll(productList);
    }

    public void delete(int id) {
    	Optional<Product> optionalProduct = productRepository.findById(id);
    	if(optionalProduct.isPresent()) {
    		productRepository.deleteById(id);
    	}else {
    		logger.warn(String.format("No Product found with ID %d", id));
    		throw new ProductNotFoundException(String.format("No product found with ID %d", id));
    	}
        
    }
    
    public List<Product> findByNameContains(String name){
    	return productRepository.findByNameContains(name);
    }
    
    public List<Product> findByPriceRange(double minPrice, double maxPrice){
    	return productRepository.findByPriceRange(minPrice,maxPrice);
    }
    
    public List<Product> filterByRating(){
    	return productRepository.filterByRating();
    }
    
}
