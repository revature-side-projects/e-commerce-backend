package com.revature.controllers;

import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.dtos.AddressRequest;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Address;
import com.revature.models.User;
import com.revature.services.AddressService;
import com.revature.services.UserService;

@RestController
@RequestMapping("/api/addresses")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:3000" }, allowCredentials = "true")
public class AddressController {

	private final AddressService aserv;
	private final UserService userv;

	public AddressController(AddressService aserv, UserService userv) {
		this.aserv = aserv;
		this.userv = userv;
	}

	@GetMapping("/{userId}") // TODO: Strongly consider using "/user/{userId}" isntead.
	public ResponseEntity<Set<Address>> getUserAddresses(@PathVariable("userId") int userId) {
		Optional<User> optionalUser = userv.findById(userId);
		if (optionalUser.isPresent()) {
			return ResponseEntity.ok(aserv.findUsersAddresses(optionalUser.get()));
		} else {
			throw new UserNotFoundException(userId);
		}
	}

	@Authorized
	@PutMapping("/{id}")
	public ResponseEntity<Address> updateAddress(@RequestBody AddressRequest addressRequest, @PathVariable("id") int id,
			HttpSession session) {
		User u = (User) session.getAttribute("user");

		return ResponseEntity.ok(aserv.update(addressRequest, id, u));

	}

	@Authorized
	@PostMapping
	public ResponseEntity<Address> addAddress(@RequestBody AddressRequest addressRequest, HttpSession session) {
		User u = (User) session.getAttribute("user");
		return ResponseEntity.ok(aserv.addAddress(addressRequest, u));
	}
}
