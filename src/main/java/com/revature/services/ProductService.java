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

    public void delete(int id);
}
