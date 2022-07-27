package com.revature.dtos;

import java.util.Set;

import com.revature.models.Address;
import com.revature.models.Purchase;
import com.revature.models.Review;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
	private int id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String role;
//	private Set<Purchase> purchases;
//	private Set<Review> reviews;
}
