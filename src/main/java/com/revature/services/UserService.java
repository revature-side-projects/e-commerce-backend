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

	/**
	 * Finds a User by credentials
	 * @param email - String
	 * @param password - String
	 * @return Optional<User> or UserNotFoundException
	 */
	public Optional<User> findByCredentials(String email, String password) {
		Optional<User> optionalUser = userRepository.findByEmailAndPassword(email, password);

		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException(String.format("No user found with email %s", email));
		}

		return optionalUser;
	}

	/**
	 * Finds a User by Id
	 * @param id - int
	 * @return Optional<User> or throws UserNotFoundException
	 */
	public Optional<User> findById(int id) {
		Optional<User> optionalUser = userRepository.findById(id);

		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException(String.format("No user found with ID %d", id));
		}

		return optionalUser;
	}

	/**
	 * Finds a User by Email
	 * @param email - String
	 * @return Optional<User> or throws UserNotFoundException
	 */
	public Optional<User> findByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);

		if (!optionalUser.isPresent()) {
			throw new UserNotFoundException(String.format("No user found with Email " + email));
		}
		return optionalUser;
	}

	/**
	 * Save or updates an existing User
	 * @param user - User
	 * @return User
	 */
	public User save(User user) {
		return userRepository.save(user);
	}
}
