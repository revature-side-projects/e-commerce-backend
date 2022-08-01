package com.revature.dtos;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUpdateRequest {
    private int id;
    @BusinessKey
    private int quantity;
    @BusinessKey
    private double price;
    @BusinessKey
    private String description;
    @BusinessKey
    private String image;
    @BusinessKey
    private String name;

    @Override
    public String toString() { return BusinessIdentity.toString(this); }

    @Override
    public boolean equals(final Object o) { return BusinessIdentity.areEqual(this, o); }

    @Override
    public int hashCode() { return BusinessIdentity.getHashCode(this); }
}
