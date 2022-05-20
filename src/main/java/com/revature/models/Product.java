package com.revature.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int quantity;
    private double price;
    @Column(name = "saleRate")
    private double sale_rate;
    @Column(name = "saleFlat")
    private double sale_flat;
    private String description;
    private String image;
    private String name;
    @Column(name = "is_sale")
    private boolean isSale;
}
