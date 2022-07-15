package com.revature.models;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "product_review")
public class ProductReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_review_id", nullable = false)
    private Integer product_review_id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user_id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product_id;

    @Column
    private String description;

    @Column(nullable = false)
    private int rating;

    public ProductReview() {
    }

    public ProductReview(Integer product_review_id, User user_id, Product product_id, String description, int rating) {
        this.product_review_id = product_review_id;
        this.user_id = user_id;
        this.product_id = product_id;
        this.description = description;
        this.rating = rating;
    }

    public Integer getProduct_review_id() {
        return product_review_id;
    }

    public void setProduct_review_id(Integer product_review_id) {
        this.product_review_id = product_review_id;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }

    public Product getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Product product_id) {
        this.product_id = product_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductReview that = (ProductReview) o;
        return rating == that.rating && Objects.equals(product_review_id, that.product_review_id) && Objects.equals(user_id, that.user_id) && Objects.equals(product_id, that.product_id) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(product_review_id, user_id, product_id, description, rating);
    }

    @Override
    public String toString() {
        return "ProductReview{" +
                "product_review_id=" + product_review_id +
                ", user_id=" + user_id +
                ", product_id=" + product_id +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                '}';
    }
}
