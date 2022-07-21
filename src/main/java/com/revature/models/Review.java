package com.revature.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reviews")
@Data @AllArgsConstructor @NoArgsConstructor
public class Review {
	@Id
	@Column(name = "review_id")
	@GeneratedValue
	private int id;

	@Min(value = 0)
	@Max(value = 5)
	private int stars;

	@Length(max = 100)
	private String title;

	@Length(max = 400)
	private String review;

	@Column(name = "posted")
	private Timestamp posted;

	@Column(name = "updated")
	private Timestamp updated;

	@ManyToOne
	@JoinColumn(name = "User.id", nullable = false)
	private User userId;

	@ManyToOne
	@JoinColumn(name = "Product.id", nullable = false)
	private Product productId;

	public Review(@NotBlank int stars, @Length(max = 100) String title, @Length(max = 400) String review,
			Timestamp posted, Timestamp updated, User userId, Product productId) {
		super();
		this.stars = stars;
		this.title = title;
		this.review = review;
		this.posted = posted;
		this.updated = updated;
		this.userId = userId;
		this.productId = productId;
	}

}
