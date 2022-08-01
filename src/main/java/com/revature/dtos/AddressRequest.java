package com.revature.dtos;

import com.revature.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
	private Integer id;
	private String street;
	private String secondary;
	private String city;
	private String zip;
	private String state;
}
