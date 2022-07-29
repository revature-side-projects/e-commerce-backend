package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.dtos.PurchaseRequest;
import com.revature.exceptions.ProductNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Product;
import com.revature.models.Purchase;
import com.revature.models.User;
import com.revature.repositories.PurchaseRepository;

/**
 * 
 * This PurchaseService will be used within our PurchaseController. Its role is to call upon our 
 * PurchaseRepository's abstracted and custom methods to persist/gather information from the database.
 * 
 * This service currently has 3 methods: 2 finds, and 1 add.
 */

@Service
public class PurchaseService {
	
	private final PurchaseRepository purchaseRepo;
	private final ProductService pserv;
	private final UserService userv;
	
	public PurchaseService(PurchaseRepository purchaseRepo, ProductService pserv,
			UserService userv) {
		this.purchaseRepo = purchaseRepo;
		this.pserv = pserv;
		this.userv = userv;
	}
	
	
	/**
	 * This method calls the PurchaseRepository's findAll() method to return a 
	 * list of all the purchases currently in the database.
	 * 
	 * @return a List of Purchase Objects. 
	 */
	public List<Purchase> findAll() {
		return purchaseRepo.findAll();
	}
	
	public List<Purchase> findByOwner(int userId) {
		Optional<User> optionalUser = userv.findById(userId);
		if(optionalUser.isPresent()) {
			return purchaseRepo.findByOwnerUser(optionalUser.get());
		} else {
			throw new UserNotFoundException(userId);
		}
	}
	
	/**
	 * This method is used to add a new purchase to the database.
	 * 
	 * @param purchaseRequest - a body that contains the purchase information from the front end.
	 * @param user - the user associated with the purchase.
	 * 
	 * sets additional information about the purchase, such as which product, the quantity, and the owner.
	 * 
	 * @return the purchase that was saved in the database.
	 */
	public Purchase add(PurchaseRequest purchaseRequest, User user) {
		Optional<Product> optionalProduct = pserv.findById(purchaseRequest.getId());
		if(optionalProduct.isPresent()) {
			Purchase purchase = new Purchase();
			purchase.setProduct(optionalProduct.get());
			purchase.setOwnerUser(user);
			purchase.setQuantity(purchaseRequest.getQuantity());
			return purchaseRepo.save(purchase);
		} else {
			throw new ProductNotFoundException(purchaseRequest.getId());
		}
	}

}
