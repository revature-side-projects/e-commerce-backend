package com.revature.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import com.revature.annotations.Authorized;
import com.revature.dtos.PurchaseRequest;
import com.revature.models.Purchase;
import com.revature.models.User;
import com.revature.services.PurchaseService;

@RestController
@RequestMapping("api/purchases")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class PurchaseController {
	
	private final PurchaseService pserv;
	
	public PurchaseController(PurchaseService pserv) {
		this.pserv = pserv;
	}
	
	@Authorized
	@GetMapping
	public ResponseEntity<List<Purchase>> getAllPurchases() {
		return ResponseEntity.ok(pserv.findAll());
	}
	
	@Authorized
	@GetMapping("user/{user}")
	public ResponseEntity<List<Purchase>> getPurchasesByOwner(@PathVariable("user") User user) {
		return ResponseEntity.ok(pserv.findByOwner(user));
	}
	
	@Authorized
	@PostMapping
	public ResponseEntity<Purchase> addPurchase(@RequestBody PurchaseRequest purchaseRequest, HttpSession session) {
		User u = (User) session.getAttribute("user");
		
		try {
			return ResponseEntity.ok(pserv.add(purchaseRequest, u));
		} catch(ResourceAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
