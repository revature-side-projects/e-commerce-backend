package com.revature.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    @Min(value = 0)
	@Max(value = 5)
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
    @JsonManagedReference
    private Product product;
    

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @JsonManagedReference
    private User user;
    
    
    public Review(@NotBlank int stars, @Length(max = 100) String title, @Length(max = 400) String review,
			Timestamp posted, Timestamp updated, User user, Product product) {
		super();
		this.stars = stars;
		this.title = title;
		this.review = review;
		this.user = user;
		this.product = product;
	}

}
