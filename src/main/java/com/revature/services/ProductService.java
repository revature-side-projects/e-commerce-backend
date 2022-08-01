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

/**
 * Calls ProductRepository to perform CRUD operations on Products. 
 * Also performs filtering on products.
 *
 */
@Service
public class ProductService {

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Finds all products in productRepository
     * 
     * @return List of Products
     */
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    /**
     * Finds a product if it exists in the database.
     * @param id - Product's Id
     * @return Optional<Product> or ProductNotFoundException
     */
    public Optional<Product> findById(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (!optionalProduct.isPresent()) {
        
            logger.warn(String.format("No product found with ID %d", id));
            throw new ProductNotFoundException(String.format("No product found with ID %d", id));
        }
        logger.info(String.format("Product with ID: %d successfully found", optionalProduct.get().getId()));
        return optionalProduct;
    }

    /**
     * Creates or Updates an existing Product.
     * @param product - Product
     * @return Product
     */
    public Product save(Product product) {
        return productRepository.save(product);
    }

    /**
     * Saves or updates all elements in productList
     * @param productList - List<Product>
     * @param metadata - List<Product>
     * @return <List>Product
     */
    public List<Product> saveAll(List<Product> productList, List<ProductInfo> metadata) {
        return productRepository.saveAll(productList);
    }

    /**
     * Deletes a product by Id or throws ProductNotFoundException
     * @param id - int
     */
    public void delete(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            productRepository.deleteById(id);
        } else {
            logger.warn(noId(id));
            throw new ProductNotFoundException(noId(id));
        }

    }

    /**
     * Filters ProductRepository by any Product whose name contains the search input. Returns that list.
     * @param name - String
     * @return List<Product>
     */
    public List<Product> findByNameContains(String name) {
        return productRepository.findByNameContains(name);
    }

    /**
     * Finds all Products whose price falls between minPrice and maxPrice.
     * @param minPrice
     * @param maxPrice
     * @return List<Product>
     */
    public List<Product> findByPriceRange(double minPrice, double maxPrice) {
        return productRepository.findByPriceRange(minPrice, maxPrice);
    }

    /**
     * Sorts the products by rating in descending order.
     * @return List<Product>
     */
    public List<Product> filterByRating() {
        return productRepository.filterByRating();
    }

    private String noId(int id) {
            logger.warn(String.format("No Product found with ID %d", id));
            throw new ProductNotFoundException(String.format("No product found with ID %d", id));
        }
}
