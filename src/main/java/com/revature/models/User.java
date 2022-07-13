package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name= "email", nullable = false)
	@NotEmpty
	@Email
    private String email;
	
	@Column(name = "password")
	@NotNull
	@Size(min = 6, message= "password should have at least 6 characters")
    private String password;
	
	@Column(name = "firstName")
    @NotEmpty
    @Size(min = 3, message = "firstname should be at least 3 characters")
    private String firstName;
    
	@Column(name = "lastName")
    @NotNull
    @Size(max = 10, message = "lastname should be not more than 10 characters")
    private String lastName;
}
