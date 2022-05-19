package com.revature.services;

import com.revature.dtos.ProductInfo;
import com.revature.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    public List<Product> findAll();

    public Optional<Product> findById(int id);

    public Product save(Product product);

    public List<Product> saveAll(List<Product> productList, List<ProductInfo> metadata);

    /**
     * Filters the list of product names based on whether the searchParam is contained.
     * @param searchParam The string
     * @return The filtered list
     */
    public List<Product> searchProduct(String searchParam);

    public void delete(int id);
}
