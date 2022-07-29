package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "addresses")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "users" })
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int id;
    @NotNull    @NonNull

    private String street;
    private String secondary;

    @NotNull    @NonNull

    private String city;
    @NotNull    @NonNull

    private String zip;
    @NotNull    @NonNull

    private String state;

    @ManyToMany
    @JoinTable(name = "users_addresses",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private Set<User> users = new LinkedHashSet<>();

}