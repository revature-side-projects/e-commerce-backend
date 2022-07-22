package com.revature.models;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    @NotNull
    private int id;

//    @NotNull
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

}
