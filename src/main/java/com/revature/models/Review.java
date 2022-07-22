package com.revature.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.sql.Timestamp;

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
    // min and max
    private int stars;

    @NotNull
    private String title;

    @NotNull
    private String review;

    @CreationTimestamp
    private Timestamp posted;

    @UpdateTimestamp
    private Timestamp updated;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

}
