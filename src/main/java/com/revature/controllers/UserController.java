package com.revature.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.revature.annotations.Authorized;
import com.revature.models.User;
import com.revature.services.UserService;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:3000"}, allowCredentials = "true")
public class UserController {
	
	private final UserService userv;
	
	public UserController(UserService userv) {
		this.userv = userv;
	}
	
	@Authorized
	@GetMapping("/{userId}")
	public ResponseEntity<User> getUserById(@PathVariable("userId") int userId) {
	
		Optional<User> optionalUser = userv.findById(userId);
		
		return ResponseEntity.ok(optionalUser.get());
	}
	
	@Authorized
	@PutMapping
	public ResponseEntity<User> update(@RequestBody User user) {
		return ResponseEntity.ok(userv.save(user));
	}
}
