package com.revature.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
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
    public Review(@NotBlank int stars, @Length(max = 100) String title, @Length(max = 400) String review, User user, Product product) {
		super();
		this.stars = stars;
		this.title = title;
		this.review = review;
		this.user = user;
		this.product = product;
	}

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
