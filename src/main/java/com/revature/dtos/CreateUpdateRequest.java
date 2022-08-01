package com.revature.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateRequest {
    private int id;
    private int quantity;
    private double price;
    private String description;
    private String image;
    private String name;

}
