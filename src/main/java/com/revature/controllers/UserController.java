package com.revature.controllers;

import java.util.HashSet;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.dtos.UserRequest;
import com.revature.models.User;
import com.revature.services.UserService;

/**
 * UserController is in charge of handling HTTP requests at the api/users
 * url.
 * 
 * It has a private UserService which will be used to call on the
 * UserRepository and
 * persist user information to the database.
 *
 */
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

	private final UserService userv;

	public UserController(UserService userv) {
		this.userv = userv;
	}

	/**
	 * Gets a User by Id
	 * @param userId
	 * @return Http response with a User or Http response with Not found status
	 */
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") int userId) {

		Optional<User> optionalUser = userv.findById(userId);

		return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	/**
	 * Gets a User by Email.
	 * @param userEmail - full email is expected in url path variable
	 * @return Http response with body of User or a 404 not found status
	 */
	@GetMapping("/email/{userEmail}")
	public ResponseEntity<User> getUserByEmail(@PathVariable("userEmail") String userEmail) {
		Optional<User> optionalUser = userv.findByEmail(userEmail);
		if(!optionalUser.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		User user = optionalUser.get();
		User withoutPasswordUser = new User(user.getId(), user.getEmail(), "", user.getFirstName(), user.getLastName(), user.getRole(),
				new HashSet<>(), new HashSet<>(), new HashSet<>());
		return ResponseEntity.ok(withoutPasswordUser);
	}

	/**
	 * Updates a User
	 * 
	 * @param user - Http request body containing a User object
	 * @return Http response with body of User or a 404 not found status
	 */
	@PutMapping
	public ResponseEntity<User> update(@RequestBody User user) {
		Optional<User> newUserOptional = userv.findById(user.getId());
		if(!newUserOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		User newUser = newUserOptional.get();
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		return ResponseEntity.ok(userv.save(newUser));
	}

	/**
	 * Creates a new User
	 * 
	 * @param user - Http request body containing a User object
	 * @return Http response with body of User
	 */
	@PostMapping
	public ResponseEntity<User> registerUser(@RequestBody UserRequest user) {
		User newUser = new User(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(),
				user.getRole());
		return ResponseEntity.ok(userv.save(newUser));
	}
}
