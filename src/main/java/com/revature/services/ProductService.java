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
     * Filters the list of product names and descriptions based on whether the searchParam is contained or the total list
     * if an empty string is supplied.
     * @param searchParam The search keyword or the empty string.
     * @return The filtered list containing products whose names or descriptions contain the search keyword if not the
     * empty string. The full list of products, otherwise.
     */
    public List<Product> searchProduct(String searchParam);

    public void delete(int id);
}
