package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.dtos.PriceRangeRequest;
import com.revature.dtos.ProductInfo;
import com.revature.models.Product;
import com.revature.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    //@Authorized
    @GetMapping
    public ResponseEntity<List<Product>> getInventory() {
        return ResponseEntity.ok(productService.findAll());
    }

    //@Authorized
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        Optional<Product> optional = productService.findById(id);

        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Authorized
    @PutMapping("/create-update")
    public ResponseEntity<Product> upsert(@RequestBody Product product) {
        return ResponseEntity.ok(productService.save(product));
    }

    @Authorized
    @PatchMapping
    public ResponseEntity<List<Product>> purchase(@RequestBody List<ProductInfo> metadata) { 	
    	List<Product> productList = new ArrayList<Product>();
    	
    	for (int i = 0; i < metadata.size(); i++) {
    		Optional<Product> optional = productService.findById(metadata.get(i).getId());

    		if(!optional.isPresent()) {
    			return ResponseEntity.notFound().build();
    		}

    		Product product = optional.get();

    		if(product.getQuantity() - metadata.get(i).getQuantity() < 0) {
    			return ResponseEntity.badRequest().build();
    		}
    		
    		product.setQuantity(product.getQuantity() - metadata.get(i).getQuantity());
    		productList.add(product);
    	}
        
        productService.saveAll(productList, metadata);

        return ResponseEntity.ok(productList);
    }

    @Authorized
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {
        Optional<Product> optional = productService.findById(id);

        if(!optional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        productService.delete(id);

        return ResponseEntity.ok(optional.get());
    }
    @Authorized
    @GetMapping("/partial-search/{name}")
    public ResponseEntity<List<Product>> getProductsByNameContains(@PathVariable("name") String name) {
    	
        return ResponseEntity.ok(productService.findByNameContains(name));
    }
    @Authorized
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@RequestBody PriceRangeRequest priceRangeRequest) {
    	
        return ResponseEntity.ok(productService.findByPriceRange(priceRangeRequest.getMinPrice(),priceRangeRequest.getMaxPrice()));
    }
//    @Authorized
//    @GetMapping("/price-range")
//    public ResponseEntity<List<Product>> getProductsByPriceRange(@RequestParam("minPrice") double minPrice,@RequestParam("maxPrice") double maxPrice ) {
//    	
//        return ResponseEntity.ok(productService.findByPriceRange(minPrice,maxPrice));
//    }
    @Authorized
    @GetMapping("/filter-rating")
    public ResponseEntity<List<Product>> filterByRating() {
    	
        return ResponseEntity.ok(productService.filterByRating());
    }
    

    
}
