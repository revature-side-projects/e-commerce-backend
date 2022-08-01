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

@Service
public class AddressService {

	private final AddressRepository addressRepo;
	private final UserService userService;

	public AddressService(AddressRepository addressRepo, UserService userService) {
		this.addressRepo = addressRepo;
		this.userService = userService;
	}

	public Address addAddress(AddressRequest addressRequest, User u) {
		Address address = new Address(addressRequest.getStreet(), addressRequest.getCity(), addressRequest.getZip(), addressRequest.getState());
		Set<User> users = new HashSet<>();
		users.add(u);
		address.setSecondary(addressRequest.getSecondary());
		address.setUsers(users);
		return addressRepo.save(address);
	}

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

	public Set<Address> findUsersAddresses(User u) {
		return addressRepo.findByUsers(u);
	}
}
