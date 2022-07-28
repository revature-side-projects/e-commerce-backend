package com.revature.services;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.revature.dtos.AddressRequest;
import com.revature.models.Address;
import com.revature.models.User;
import com.revature.repositories.AddressRepository;

@Service
public class AddressService {
	
	private final AddressRepository addressRepo;
	
	public AddressService(AddressRepository addressRepo) {
		this.addressRepo = addressRepo;
	}
	
	public Address addAddress(AddressRequest addressRequest) {
		
		Address address = new Address();
		address.setStreet(addressRequest.getStreet());
		address.setSecondary(addressRequest.getSecondary());
		address.setCity(addressRequest.getCity());
		address.setState(addressRequest.getState());
		address.setZip(addressRequest.getZip());
//		address.getUsers().add(addressRequest.getUser());
		return addressRepo.save(address);
	}
	
	public Address update(AddressRequest addressRequest, User u) {
		
		Address address = new Address();
//		address.setId(addressRequest.getId());
		address.setStreet(addressRequest.getStreet());
		address.setSecondary(addressRequest.getSecondary());
		address.setCity(addressRequest.getCity());
		address.setState(addressRequest.getState());
		address.setZip(addressRequest.getZip());
		address.getUsers().add(u);
		
		return addressRepo.save(address);
	}
	
	public Set<Address> findUsersAddresses(User u) {
		
		return addressRepo.findByUsers(u);
	}

}
