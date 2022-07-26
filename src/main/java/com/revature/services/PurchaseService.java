package com.revature.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.dtos.PurchaseRequest;
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
	
	public List<Purchase> findByOwner(User user) {
		return purchaseRepo.findByOwnerUser(user);
	}
	
	public Purchase add(PurchaseRequest purchaseRequest, User user) {
		Optional<Product> optionalProduct = pserv.findById(purchaseRequest.getProductId());
		
		Purchase purchase = new Purchase();
		purchase.setProduct(optionalProduct.get());
		purchase.setOwnerUser(user);
		return purchaseRepo.save(purchase);
	}

}
