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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;
    @NotNull
    private int quantity;
    @NotNull
    private double price;
    @NotNull
    private String description;
    @NotNull
    private String image;
    @NotNull
    private String name;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    private Set<Purchase> purchases = new LinkedHashSet<>();
}
