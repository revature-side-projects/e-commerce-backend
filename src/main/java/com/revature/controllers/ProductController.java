package com.revature.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.dtos.CreateUpdateRequest;
import com.revature.dtos.PriceRangeRequest;
import com.revature.dtos.ProductInfo;
import com.revature.exceptions.InvalidProductInputException;
import com.revature.models.Product;
import com.revature.services.ProductService;
import com.revature.services.StorageService;

/**
 * Handles Http requests to endpoints at /api/product
 *
 */
@RestController
@RequestMapping("/api/product")
@CrossOrigin(origins = "*")
public class ProductController {

	private final ProductService productService;
	private final StorageService s3Srv;

	public ProductController(ProductService productService, StorageService storageService) {
		this.productService = productService;
		this.s3Srv = storageService;
	}

	/**
	 * Gets all products in inventory
	 * 
	 * @return HttpResponse with Body of an array of Products.
	 */
	@GetMapping
	public ResponseEntity<List<Product>> getInventory() {
		return ResponseEntity.ok(productService.findAll());
	}

	/**
	 * Gets a single product by its id
	 * @param id
	 * @return HttpResponse with Body of a Product
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable("id") int id) {
		Optional<Product> optional = productService.findById(id);

		return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * Updates a Product.
	 * @param createupdateRequest - Body of type Product. 
	 * @return HttpResponse with Body of a Product
	 */
	@PutMapping("/create-update")
	public ResponseEntity<Product> insertAndUpdate(@RequestBody CreateUpdateRequest createupdateRequest) {
		int id = createupdateRequest.getId();
		Optional<Product> currPd = productService.findById(id);

		int quantity = createupdateRequest.getQuantity();
		double price = createupdateRequest.getPrice();
		String description = createupdateRequest.getDescription();
		String image = createupdateRequest.getImage();
		String name = createupdateRequest.getName();

		if (currPd.isPresent()) {

			Product updatePd = currPd.get();
			updatePd.setId(id);
			if (name != null) {
				updatePd.setName(name);
			}

			if (price > 0) {
				updatePd.setPrice(price);
			}

			if (quantity > 0) {

				updatePd.setQuantity(quantity);
			}
			if (description != null) {
				updatePd.setDescription(description);
			}
			if (image != null) {
				updatePd.setImage(image);

			}

			return ResponseEntity.ok(productService.save(updatePd));
		}
		Product newPd;
		try {
			newPd = new Product(quantity, price, description, image, name);
		} catch (Exception e) {

			throw new InvalidProductInputException("Null value is not allowed");
		}
		return ResponseEntity.ok(productService.save(newPd));
	}

	/**
	 * Takes a file sent with request and sends to AWS S3 Service
	 * @param file
	 * @return HttpResponse with String of the file
	 */
	@PutMapping("/uploadFile")
	public ResponseEntity<String> uploadImage(@RequestPart(value = "file") MultipartFile file) {
		return this.s3Srv.uploadFile(file);
	}

	/**
	 * Decrease inventory quantity stored by amount of product purchased.
	 * @param metadata - Http body of an array of Products
	 * @return HttpResponse with the array of Products sent in request. Or an exception
	 */
	@PatchMapping
	public ResponseEntity<List<Product>> purchase(@RequestBody List<ProductInfo> metadata) {
		List<Product> productList = new ArrayList<Product>();

		for (int i = 0; i < metadata.size(); i++) {
			// Gets product from product repository
			Optional<Product> optional = productService.findById(metadata.get(i).getId());

			if (!optional.isPresent()) {
				return ResponseEntity.notFound().build();
			}

			Product product = optional.get();

			// not enough product in inventory to fulfill order
			if (product.getQuantity() - metadata.get(i).getQuantity() < 0) {
				return ResponseEntity.badRequest().build();
			}
			// update new inventory in product
			product.setQuantity(product.getQuantity() - metadata.get(i).getQuantity());
			productList.add(product);
		}
		
		// saves new product inventory in repository
		productService.saveAll(productList, metadata);

		return ResponseEntity.ok(productList);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Product> deleteProduct(@PathVariable("id") int id) {
		Optional<Product> optional = productService.findById(id);

		if (!optional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		productService.delete(id);

		return ResponseEntity.ok(optional.get());
	}

    @GetMapping("/partial-search/{name}")
    public ResponseEntity<List<Product>> getProductsByNameContains(@PathVariable("name") String name) {

        return ResponseEntity.ok(productService.findByNameContains(name));
    }
    @GetMapping("/price-range")
    public ResponseEntity<List<Product>> getProductsByPriceRange(@RequestBody PriceRangeRequest priceRangeRequest) {

        return ResponseEntity.ok(productService.findByPriceRange(priceRangeRequest.getMinPrice(),priceRangeRequest.getMaxPrice()));
    }

    @GetMapping("/filter-rating")
    public ResponseEntity<List<Product>> filterByRating() {

        return ResponseEntity.ok(productService.filterByRating());
    }
}
