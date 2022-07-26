package com.revature.dtos;

import com.revature.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This contains the metadata required for the product display
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductInfo {
    private Integer productId;
    private String name;
    private String description;
    private double price;
    private String imgUrlSmall;
    private String imgUrlMed;
    private String category;
    private int numberOfRatings;
    private int sumOfRatings;

    public ProductInfo(Product product) {
        this.productId = product.getProductId();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.imgUrlSmall = product.getImageUrlS();
        this.imgUrlMed = product.getImageUrlM();
        this.category = product.getCategory().getName();
        this.numberOfRatings = product.getRatings().size();
    }

    public void setSumOfRating(int sumOfRatings) {
        this.sumOfRatings = sumOfRatings;
    }
}
