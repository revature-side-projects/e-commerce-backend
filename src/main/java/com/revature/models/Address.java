package com.revature.models;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int id;
    @NotNull
    private String street;
    private String secondary;

    @NotNull
    private String city;
    @NotNull
    private String zip;
    @NotNull
    private String state;

    @ManyToMany
    @JoinTable(name = "users_addresses",
            joinColumns = @JoinColumn(name = "address_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private Set<User> users = new LinkedHashSet<>();
}
