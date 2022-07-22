package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    @NotBlank
    @Column(unique=true)
    @Email
    private String email;
    
    @NotBlank
    @Length(min=8)
    private String password;
    
    @NotBlank
    private String firstName;
    
    @NotBlank
    private String lastName;
    
    @ManyToMany(mappedBy="address_id")
    private List<Address> address;
    
    @OneToMany(mappedBy="")
    private List<Review> review;
    
    @OneToMany(mappedBy="")
    private List<Purchases> product;
    
    

	public User(int id, String email, String password, String firstName, String lastName) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}



	public User(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
    
    
    
    
}
