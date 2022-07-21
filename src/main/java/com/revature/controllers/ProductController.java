package com.revature.controllers;

import com.revature.annotations.AdminOnly;
import com.revature.dtos.CreateProduct;
import com.revature.dtos.ProductInfo;
import com.revature.exceptions.NotImplementedException;
import com.revature.models.Product;
import com.revature.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public ResponseEntity getInventory() {
        return productService.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/rating/{id}")
    public ResponseEntity getProductReviewsById(@PathVariable("id") int id) {
        return productService.findReviewsByProductId(id);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public ProductInfo getProductById(@PathVariable("id") int id) {
        return productService.findById(id);
    }

    @AdminOnly
    @PutMapping
    public void insert(@RequestBody ProductInfo product) {
        throw new NotImplementedException();
    }

    @AdminOnly
    @PatchMapping
    public ResponseEntity<List<ProductInfo>> purchase(@RequestBody List<ProductInfo> metadata) {
        throw new NotImplementedException();
    }

    @AdminOnly
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductInfo> deleteProduct(@PathVariable("id") int id) {
        throw new NotImplementedException();
    }

    /**
     *  POST endpoint that utilizes the ProductService to persist a new product to the database
     * @param createProduct DTO that maps to the product model
     */
    @ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping( value = "/createproduct", consumes = "application/json", produces = "application/json")
    public void newProduct(@RequestBody CreateProduct createProduct) {
        Product newProduct = new Product(createProduct);
        productService.save(newProduct);

    }
}
