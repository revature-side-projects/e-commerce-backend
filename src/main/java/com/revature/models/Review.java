package com.revature.models;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private int id;

    @NotNull
    @NonNull
    @Min(value = 0)
    @Max(value = 5)
    private int stars;

    @NotNull
    @NonNull
    private String title;

    @NotNull
    @NonNull
    private String review;

    @CreationTimestamp
    private Timestamp posted;

    @UpdateTimestamp
    private Timestamp updated;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    @NonNull
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    @NonNull
    private User user;

    public Review(@NotBlank int stars, @Length(max = 100) String title, @Length(max = 400) String review, User user,
            Product product) {
        super();
        this.stars = stars;
        this.title = title;
        this.review = review;
        this.user = user;
        this.product = product;
    }
}
