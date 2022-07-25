package com.revature.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
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
    @ToString.Exclude
    private Set<Review> reviews = new LinkedHashSet<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "product_id")
    @JsonBackReference
    @ToString.Exclude
    private Set<Purchase> purchases = new LinkedHashSet<>();

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
