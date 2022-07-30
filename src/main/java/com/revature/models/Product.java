package com.revature.models;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
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
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @ToString.Exclude
    private Set<Purchase> purchases = new LinkedHashSet<>();


    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
