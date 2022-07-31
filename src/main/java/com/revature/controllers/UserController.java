package com.revature.controllers;

import java.util.HashSet;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
		User withoutPasswordUser = new User(user.getId(), userEmail, "", user.getFirstName(), user.getLastName(), "",
				new HashSet<>(), new HashSet<>(), new HashSet<>());
		return ResponseEntity.ok(withoutPasswordUser);
	}

	@PutMapping
	public ResponseEntity<User> update(@RequestBody UserRequest user, HttpSession session) {

		User curUser = (User) session.getAttribute("user");
		System.out.println(curUser);
		curUser.setEmail(user.getEmail());
		curUser.setPassword(user.getPassword());
		curUser.setFirstName(user.getFirstName());
		curUser.setLastName(user.getLastName());
		// curUser.setAddresses(user.getAddresses());
		System.out.println(curUser);

		return ResponseEntity.ok(userv.save(curUser));
	}

	@PostMapping
	public ResponseEntity<User> registerUser(@RequestBody UserRequest user) {
		User newUser = new User(user.getEmail(), user.getPassword(), user.getFirstName(), user.getLastName(),
				user.getRole());
		return ResponseEntity.ok(userv.save(newUser));
	}
}
