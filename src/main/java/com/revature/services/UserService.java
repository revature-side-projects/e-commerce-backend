package com.revature.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.revature.exceptions.UserNotFoundException;
import com.revature.models.User;
import com.revature.repositories.UserRepository;

@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Optional<User> findByCredentials(String email, String password) {
		Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);

		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException(String.format("No user found with email %s", email));
		}

		return optionalUser;
	}

	public Optional<User> findById(int id) {
		Optional<User> optionalUser = userRepository.findById(id);

		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException(String.format("No user found with ID %d", id));
		}

		return optionalUser;
	}

	public Optional<User> findByEmail(String email) {
		return userRepository.findByEmail(email);

	}

	public User save(User user) {
		return userRepository.save(user);
	}
}
