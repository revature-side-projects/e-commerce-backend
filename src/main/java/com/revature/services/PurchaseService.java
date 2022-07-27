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
