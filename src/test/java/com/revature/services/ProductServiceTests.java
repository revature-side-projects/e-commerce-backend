package com.revature.services;

import com.revature.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ProductServiceTests {

    @Autowired
    ProductService productService;

    @Test
    void find_by_name_or_description() {
        List<Product> retrieved = productService.searchProduct("ba");
        retrieved.stream().forEach(p -> Assertions.assertTrue(
                p.getDescription().toLowerCase().contains("ba") || p.getName().toLowerCase().contains("ba")
        ));

        retrieved = productService.searchProduct("nice");
        retrieved.stream().forEach(p -> Assertions.assertTrue(
                p.getDescription().toLowerCase().contains("nice") || p.getName().toLowerCase().contains("nice")
        ));
    }

    @Test
    void find_by_gibberish() {
        List<Product> retrieved = productService.searchProduct("dbkbzkvbzbvj");
        Assertions.assertEquals(0, retrieved.size());
    }

    @Test
    void empty_search_param_returns_all_items() {
        List<Product> retrieved = productService.searchProduct("");
        Assertions.assertEquals(productService.findAll().size(), retrieved.size());
    }

    @Test
    void null_search_param_returns_all_items() {
        List<Product> retrieved = productService.searchProduct(null);
        Assertions.assertEquals(productService.findAll().size(), retrieved.size());
    }
}
