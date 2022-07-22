package com.revature.models;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.*;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(
        name = "users",
        uniqueConstraints = @UniqueConstraint(columnNames={"email"})
)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    @NotNull
    private int id;
    @NotNull
    private String email;
    @NotNull
    private String password;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;

    @NotNull
    private String role;

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private Set<Purchase> purchases = new LinkedHashSet<>();

    @OneToMany(orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private Set<Review> reviews = new LinkedHashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "users_addresses",
            joinColumns = { @JoinColumn(name = "user_id") },
            inverseJoinColumns = { @JoinColumn(name = "address_id") }
    )
    private Set<Address> addresses = new HashSet<>();
}
