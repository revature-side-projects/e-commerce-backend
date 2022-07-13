package com.revature.models;

import javax.persistence.*;

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
}
