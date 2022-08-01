package com.revature.controllers;

import java.util.Optional;
import java.util.Set;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.AddressRequest;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Address;
import com.revature.models.User;
import com.revature.services.AddressService;
import com.revature.services.UserService;


/**
 * Handles Http requests to endpoints at /api/addresses
 *
 */
@RestController
@RequestMapping("/api/addresses")
@CrossOrigin(origins = "*")
public class AddressController {

	private final AddressService aserv;
	private final UserService userv;

	public AddressController(AddressService aserv, UserService userv) {
		this.aserv = aserv;
		this.userv = userv;
	}

	/**
	 * Gets a list of addresses belonging to a User.
	 * 
	 * @param userId
	 * @return HttpResponse with body containing array of User addresses 
	 */
	@GetMapping("/{userId}") // TODO: Strongly consider using "/user/{userId}" isntead.
	public ResponseEntity<Set<Address>> getUserAddresses(@PathVariable("userId") int userId) {
		Optional<User> optionalUser = userv.findById(userId);
		if (optionalUser.isPresent()) {
			return ResponseEntity.ok(aserv.findUsersAddresses(optionalUser.get()));
		} else {
			throw new UserNotFoundException(userId);
		}
	}

	/**
	 * 
	 * Updates an address belonging to a User. Must send address in request body.
	 * 
	 * @param addressRequest
	 * @param userId
	 * @return HttpResponse with body containing an Address belonging to a User
	 */
	@PutMapping("/{id}")
	public ResponseEntity<Address> updateAddress(@RequestBody AddressRequest addressRequest, @PathVariable("id") int userId) {
		Optional<User> user = userv.findById(userId);
		return ResponseEntity.ok(aserv.update(addressRequest, user.get()));

	}

	/**
	 * Creates a new address for a User.
	 * 
	 * @param addressRequest
	 * @param userId
	 * @return HttpResponse with body containing the added Address
	 */
	@PostMapping("/{id}")
	public ResponseEntity<Address> addAddress(@RequestBody AddressRequest addressRequest, @PathVariable("id") int userId) {
		Optional<User> user = userv.findById(userId);
		return ResponseEntity.ok(aserv.addAddress(addressRequest, user.get()));
	}
}
