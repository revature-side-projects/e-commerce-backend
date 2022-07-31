package com.revature.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "reviews", "purchases" })
@EqualsAndHashCode
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int id;
	@NotNull
	@NonNull
	private int quantity;
	@NotNull
	@NonNull
	private double price;
	@NotNull
	@NonNull
	private String description;
	@NotNull
	@NonNull
	private String image;
	@NotNull
	@NonNull
	private String name;

	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "product_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<Review> reviews = new LinkedHashSet<>();

	@OneToMany(orphanRemoval = true)
	@JoinColumn(name = "product_id")
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<Purchase> purchases = new LinkedHashSet<>();

	public Product(int quantity, double price, String description, String image, String name, Set<Review> reviews,
			Set<Purchase> purchases) {
		super();
		this.quantity = quantity;
		this.price = price;
		this.description = description;
		this.image = image;
		this.name = name;

	}
}
