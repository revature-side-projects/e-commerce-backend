package com.revature.controllers;

import java.util.LinkedList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.PurchaseRequest;
import com.revature.exceptions.ProductNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Purchase;
import com.revature.services.PurchaseService;

/**
 * 
 * This controller is in charge of handling HTTP requests at the api/purchases
 * url.
 * 
 * It has a private PurchaseService which will be used to call on the
 * PurchaseRepository and
 * persist purchase information to the database.
 * 
 * Currently there are 3 methods: 2 for retrieving purchases, and 1 for adding
 * them.
 */

@RestController
@RequestMapping("api/purchases")
@CrossOrigin(origins = "*")
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
	public ResponseEntity<List<Purchase>> getPurchasesByOwner(@PathVariable("user") int userId) {
		try {
			return ResponseEntity.ok(pserv.findByOwner(userId));
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("/{id}")
	public ResponseEntity<List<Purchase>> addPurchase(@RequestBody List<PurchaseRequest> purchaseRequests, @PathVariable("id") int id) {
		List<Purchase> resp = new LinkedList<>();
		try {
			for (PurchaseRequest purchaseRequest : purchaseRequests) {
				resp.add(this.pserv.add(purchaseRequest, id));
			}
		} catch (ProductNotFoundException e) {
			return ResponseEntity.notFound().build();
		}

		return ResponseEntity.ok(resp);
	}
}
