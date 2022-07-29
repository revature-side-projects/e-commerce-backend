package com.revature.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames={"email"})
)
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "purchases", "reviews", "addresses" })
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @NotNull
    @NonNull
    private String email;
    @NotNull
    @NonNull
    private String password;
    @NotNull
    @NonNull
    private String firstName;
    @NotNull
    @NonNull
    private String lastName;

    @NotNull
    @NonNull
    private String role;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private Set<Purchase> purchases = new LinkedHashSet<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private Set<Review> reviews = new LinkedHashSet<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.ALL })
    @JoinTable(
            name = "users_addresses",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "address_id") }
    )
    @ToString.Exclude
    private Set<Address> addresses = new HashSet<>();



    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}