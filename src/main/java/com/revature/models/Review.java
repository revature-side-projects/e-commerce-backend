package com.revature.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int id;
    @NotNull
    private int stars;
    @NotNull
    private String title;
    @NotNull
    private String review;
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

}
