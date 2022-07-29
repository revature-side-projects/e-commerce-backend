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

import com.revature.annotations.Authorized;
import com.revature.dtos.PurchaseRequest;
import com.revature.models.Purchase;
import com.revature.models.User;
import com.revature.services.PurchaseService;

/**
 * 
 * This controller is in charge of handling HTTP requests at the api/purchases url.
 * 
 * It has a private PurchaseService which will be used to call on the PurchaseRepository and 
 * persist purchase information to the database.
 * 
 * Currently there are 3 methods: 2 for retrieving purchases, and 1 for adding them.
 */

@RestController
@RequestMapping("api/purchases")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class PurchaseController {
	
	private final PurchaseService pserv;
	
	public PurchaseController(PurchaseService pserv) {
		this.pserv = pserv;
	}
	
	/**
	 * Has an @Authorized tag, which prevents it from being called if there is no user currently logged in.
	 * This method is in charge of retrieving a list of all purchases that are currently within the database.
	 * 
	 * @return a ResponseEntity that contains a list of all purchases within the database, as well as a HTTPStatus code.
	 */
	
	@Authorized
	@GetMapping
	public ResponseEntity<List<Purchase>> getAllPurchases() {
		return ResponseEntity.ok(pserv.findAll());
	}
	
	@Authorized
	@GetMapping("user/{user}")
	public ResponseEntity<List<Purchase>> getPurchasesByOwner(@PathVariable("user") int userId) {
		return ResponseEntity.ok(pserv.findByOwner(userId));
	}
	
	/**
	 * 
	 * @param purchaseRequests, a class that contains the necessary information to add a purchase from the front end.
	 * @param session, HttpSession 
	 * @return a ResponseEntity with a successful status and the newly added purchase,
	 * OR
	 * @return a ResponseEntity with a NotFound HttPStatus code, and a null body if a ResourceAccessException is thrown.
	 */
	
	@Authorized
	@PostMapping
	public ResponseEntity<List<Purchase>> addPurchase(@RequestBody List<PurchaseRequest> purchaseRequests, HttpSession session) {
		User u = (User) session.getAttribute("user");
		
		List<Purchase> resp = new LinkedList<>();
		for (PurchaseRequest purchaseRequest : purchaseRequests) {
			resp.add(pserv.add(purchaseRequest, u));
		}
		
		return ResponseEntity.ok(resp);
	}
}
