package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
	private int id;
	private String street;
	private String secondary;
	private String city;
	private String zip;
	private String state;
	
	public AddressRequest(String street, String secondary, String city, String zip, String state) {
		super();
		this.street = street;
		this.secondary = secondary;
		this.city = city;
		this.zip = zip;
		this.state = state;
	}
	
}
