package com.revature.controllers;

import java.util.LinkedList;
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


import com.revature.dtos.PurchaseRequest;
import com.revature.models.Purchase;
import com.revature.models.User;
import com.revature.services.PurchaseService;

@RestController
@RequestMapping("api/purchases")
@CrossOrigin(origins = "http://localhost:4200", allowCredentials = "true")
public class PurchaseController {
	
	private final PurchaseService pserv;
	
	public PurchaseController(PurchaseService pserv) {
		this.pserv = pserv;
	}
	
	@GetMapping
	public ResponseEntity<List<Purchase>> getAllPurchases() {
		return ResponseEntity.ok(pserv.findAll());
	}
	
	@GetMapping("user/{user}")
	public ResponseEntity<List<Purchase>> getPurchasesByOwner(@PathVariable("user") User user) {
		return ResponseEntity.ok(pserv.findByOwner(user));
	}
	
	@PostMapping
	public ResponseEntity<List<Purchase>> addPurchase(@RequestBody List<PurchaseRequest> purchaseRequests, HttpSession session) {
		User u = (User) session.getAttribute("user");
		
		try {
			List<Purchase> resp = new LinkedList<>();
			for (PurchaseRequest purchaseRequest : purchaseRequests) {
				resp.add(pserv.add(purchaseRequest, u));
			}
			
			return ResponseEntity.ok(resp);
		} catch(ResourceAccessException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
