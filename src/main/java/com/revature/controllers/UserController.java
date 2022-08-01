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

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

	private final UserService userv;

	public UserController(UserService userv) {
		this.userv = userv;
	}

	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") int userId) {

		Optional<User> optionalUser = userv.findById(userId);

		return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@GetMapping("/email/{userEmail}")
	public ResponseEntity<User> getUserByEmail(@PathVariable("userEmail") String userEmail) {
		Optional<User> optionalUser = userv.findByEmail(userEmail);
		User user = optionalUser.get();
		User withoutPasswordUser = new User(user.getId(), user.getEmail(), "", user.getFirstName(), user.getLastName(), user.getRole(),
				new HashSet<>(), new HashSet<>(), new HashSet<>());
		return ResponseEntity.ok(withoutPasswordUser);
	}

	@PutMapping
	public ResponseEntity<User> update(@RequestBody UserRequest user) {
		User newUser = new User(user.getId(),user.getEmail(),user.getPassword(),user.getFirstName(),user.getLastName(), user.getRole(),
				new HashSet<>(), new HashSet<>(), new HashSet<>());

		return ResponseEntity.ok(userv.save(newUser));
	}

	@PostMapping
	public ResponseEntity<User> registerUser(@RequestBody UserRequest user) {
		User newUser = new User(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(),
				user.getRole());
		return ResponseEntity.ok(userv.save(newUser));
	}
}
