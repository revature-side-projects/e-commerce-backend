package com.revature.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "purchases")
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "purchase_id")
    private int id;

    @CreationTimestamp
    @Column(name = "order_placed")
    @EqualsAndHashCode.Exclude
    private Timestamp orderPlaced;

	@BusinessKey
    @ManyToOne
    @JoinColumn(name = "product_id")
    @NotNull
    private Product product;

	@BusinessKey
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull
    private User ownerUser;

	@BusinessKey
    @Column(name = "quantity")
    private int quantity;
	
	@Override
    public String toString() { return BusinessIdentity.toString(this); }

    @Override
    public boolean equals(final Object o) { return BusinessIdentity.areEqual(this, o); }

    @Override
    public int hashCode() { return BusinessIdentity.getHashCode(this); }
}
