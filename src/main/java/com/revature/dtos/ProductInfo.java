package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.sun.istack.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
}
