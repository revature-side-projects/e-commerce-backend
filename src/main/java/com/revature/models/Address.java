package com.revature.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@RequiredArgsConstructor
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "users" })
public class Address {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int id;

    @BusinessKey
    @NotNull
    @NonNull
    private String street;
    private String secondary;

    @BusinessKey
    @NotNull
    @NonNull
    private String city;

    @BusinessKey
    @NotNull
    @NonNull
    private String zip;

    @BusinessKey
    @NotNull
    @NonNull
    private String state;

    @ManyToMany
    @JoinTable(name = "users_addresses", joinColumns = @JoinColumn(name = "address_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    @ToString.Exclude
    private Set<User> users = new LinkedHashSet<>();

    @Override
    public String toString() { return BusinessIdentity.toString(this); }

    @Override
    public boolean equals(final Object o) { return BusinessIdentity.areEqual(this, o); }

    @Override
    public int hashCode() { return BusinessIdentity.getHashCode(this); }
}