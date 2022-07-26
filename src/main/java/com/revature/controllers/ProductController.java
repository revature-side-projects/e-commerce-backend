package com.revature.controllers;

import com.revature.annotations.Authorized;
import com.revature.annotations.AuthorizedAdmin;
import com.revature.dtos.CreateUpdateRequest;
import com.revature.dtos.ProductInfo;
import com.revature.exceptions.InvalidProductInputException;
import com.revature.exceptions.InvalidRoleException;
import com.revature.models.Product;
import com.revature.services.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product" )
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Authorized
    @GetMapping
    public ResponseEntity<List<Product>> getInventory() {
        return ResponseEntity.ok(productService.findAll());
    }

    @Authorized
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
        Optional<Product> optional = productService.findById(id);

        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @Authorized
    @AuthorizedAdmin
    @PutMapping("/create-update")
    public ResponseEntity<Product> insertAndUpdate(@RequestBody CreateUpdateRequest createupdateRequest) {
    	int id =createupdateRequest.getId();
    	Optional<Product> currPd = productService.findById(id);
    	
    	int quantity = createupdateRequest.getQuantity();	
    	double price = createupdateRequest.getPrice();
    	String description = createupdateRequest.getDescription();
    	String image = createupdateRequest.getImage();
    	String name = createupdateRequest.getName();
    	
     
    	if(currPd.isPresent()) {
    	
				Product updatePd = currPd.get();
				updatePd.setId(id);
				if(!(name == null)) {
					updatePd.setName(name );
				}
				
				if(price!= 0.0) {
					updatePd.setPrice(price);
				}
  	
				if(quantity!=0) {
  
					updatePd.setQuantity(quantity);    			
				}
				if(!(description == null)) {
					updatePd.setDescription(description);
				}
				if(!(image== null)) {
					updatePd.setImage(image);
					
				}
				
				
				return ResponseEntity.ok(productService.save(updatePd));
		
				// TODO Auto-generated catch block
				
			
    		
    	}
    	Product newPd;
		try {
			newPd = new Product(quantity,price,description,image,name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new InvalidProductInputException("Null value is not allowed");
		}
    	
    	return ResponseEntity.ok(productService.save(newPd));
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
}
