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

import org.hibernate.annotations.CreationTimestamp;

import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "purchases")
//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler", "product", "ownerUser" })
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private int id;

    @CreationTimestamp
    @Column(name = "order_placed")
    private Timestamp orderPlaced;

    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User ownerUser;
    
    @Column(name = "quantity")
    private int quantity;
}
