package com.revature.services;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.revature.dtos.AddressRequest;
import com.revature.exceptions.AddressNotFoundException;
import com.revature.models.Address;
import com.revature.models.User;
import com.revature.repositories.AddressRepository;

/**
 * 
 * Can add an address, update an existing Address, or find all Addresses belonging to a User
 *
 */
@Service
public class AddressService {

	private final AddressRepository addressRepo;
	private final UserService userService;

	public AddressService(AddressRepository addressRepo, UserService userService) {
		this.addressRepo = addressRepo;
		this.userService = userService;
	}

	/**
	 * Creates a new Address and saves to Address Repository
	 * @param addressRequest - An Address
	 * @param u - A User
	 * @return Address
	 */
	public Address addAddress(AddressRequest addressRequest, User u) {
		Address address = new Address(addressRequest.getStreet(), addressRequest.getCity(),
				addressRequest.getZip(), addressRequest.getState());
		Set<User> users = new HashSet<>();
		users.add(u);
		address.setSecondary(addressRequest.getSecondary());
		address.setUsers(users);
		return addressRepo.save(address);
	}

	/**
	 * Updates an existing Address. Address must already exist or will throw exception.
	 * @param addressRequest - An Address
	 * @param user
	 * @return Address or throws AddressNotFoundException
	 */
	public Address update(AddressRequest addressRequest, User user) {
		Optional<Address> optionalAddress = addressRepo.findById(addressRequest.getId());
		if (optionalAddress.isPresent()) {
			Address address = optionalAddress.get();
			Set<User> addressesUsers = address.getUsers();
			addressesUsers.add(user);
			address.setStreet(addressRequest.getStreet());
			address.setSecondary(addressRequest.getSecondary());
			address.setCity(addressRequest.getCity());
			address.setState(addressRequest.getState());
			address.setZip(addressRequest.getZip());
			address.setUsers(addressesUsers);
			return addressRepo.save(address);
		} else {
			throw new AddressNotFoundException(addressRequest.getId());
		}
	}

	/**
	 * Finds all Addresses belonging to a User
	 * @param u - User
	 * @return Set of Addresses
	 */
	public Set<Address> findUsersAddresses(User u) {
		return addressRepo.findByUsers(u);
	}
}
