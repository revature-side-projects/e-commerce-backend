package com.revature.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.Address;

public interface AddressRepository extends JpaRepository<Address, Integer>{

}
