package com.revature.models;

import javax.persistence.*;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", updatable = false, nullable = false)
    private long product_id;

    @Column(nullable = false)
    private double price;

    private String description;
    private String image;
    private String name;

    public long getProduct_id() { return product_id; }
    public double getPrice() { return price; }
    public String getDescription() { return description; }
    public String getImage() { return image; }
    public String getName() { return name; }


}
