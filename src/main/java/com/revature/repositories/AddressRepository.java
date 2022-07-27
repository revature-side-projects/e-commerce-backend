package com.revature.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Address;
import com.revature.models.User;

public interface AddressRepository extends JpaRepository<Address, Integer>{
	
	Set<Address> findByUsers(User user); 

}
