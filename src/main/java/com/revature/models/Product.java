package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "products")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "reviews", "purchases" })
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private int id;
	@BusinessKey
	@NotNull
	@NonNull
	private int quantity;
	@BusinessKey
	@NotNull
	@NonNull
	private double price;
	@BusinessKey
	@NotNull
	@NonNull
	private String description;
	@BusinessKey
	@NotNull
	@NonNull
	private String image;
	@BusinessKey
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
	
	@Override
    public String toString() { return BusinessIdentity.toString(this); }

    @Override
    public boolean equals(final Object o) { return BusinessIdentity.areEqual(this, o); }

    @Override
    public int hashCode() { return BusinessIdentity.getHashCode(this); }
}
