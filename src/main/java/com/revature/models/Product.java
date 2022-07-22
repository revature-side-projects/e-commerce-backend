package com.revature.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

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
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "product_id")
    private Set<Purchase> purchases = new LinkedHashSet<>();
}
