package com.revature.services;

import com.revature.models.Product;
import com.revature.repositories.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceTests {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository repo;

    @Test
    void find_by_description() {
        List<Product> returned = productService.searchProduct("hat");
        Assertions.assertTrue(returned.size() > 0);
        returned.stream().forEach(p ->
                Assertions.assertTrue(p.getDescription().contains("hat") || p.getName().contains("hat"))
        );
    }
}
