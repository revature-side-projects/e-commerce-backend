package com.revature.services;

import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.revature.dtos.AddressRequest;
import com.revature.exceptions.AddressNotFoundException;
import com.revature.exceptions.UserNotFoundException;
import com.revature.models.Address;
import com.revature.models.User;
import com.revature.repositories.AddressRepository;

@Service
public class AddressService {
	
	private final AddressRepository addressRepo;
	private final UserService userService;
	
	public AddressService(AddressRepository addressRepo, UserService userService, UserService userService2) {
		this.addressRepo = addressRepo;
		this.userService = userService2;
		
	}
	
	public Address addAddress(AddressRequest addressRequest) {
		Optional<User> optionalUser = this.userService.findById(addressRequest.getId());
		
		if(optionalUser.isPresent()) {
		Address address = new Address();
		address.setStreet(addressRequest.getStreet());
		address.setSecondary(addressRequest.getSecondary());
		address.setCity(addressRequest.getCity());
		address.setState(addressRequest.getState());
		address.setZip(addressRequest.getZip());
//		address.getUsers().add(addressRequest.getUser());
		return addressRepo.save(address);
		}else {
			throw new UserNotFoundException(addressRequest.getId());
		}
	}
	
	public Address update(AddressRequest addressRequest, User u) {
		Optional<User> optionalUser = this.userService.findById(addressRequest.getId());
		
		if(optionalUser.isPresent()) {
		Address address = new Address();
		address.setId(addressRequest.getId());
		address.setStreet(addressRequest.getStreet());
		address.setSecondary(addressRequest.getSecondary());
		address.setCity(addressRequest.getCity());
		address.setState(addressRequest.getState());
		address.setZip(addressRequest.getZip());
		address.getUsers().add(u);
		
		return addressRepo.save(address);
		}else {
			throw new AddressNotFoundException(addressRequest.getId());
		}
	}
	
	public Set<Address> findUsersAddresses(User u) {
		
		return addressRepo.findByUsers(u);
	}

}
