package com.revature.services;

import com.revature.dtos.ProductInfo;
import com.revature.models.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<Product> findAll();

    List<Product> findSaleItems();

    Optional<Product> findById(int id);

    Product save(Product product);

    List<Product> saveAll(List<Product> productList, List<ProductInfo> metadata);

    List<Product> searchProduct(String searchParam);

    void delete(int id);
}
