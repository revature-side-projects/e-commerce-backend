package com.revature.dtos;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
	@BusinessKey
	private int userId;
	@BusinessKey
	private int productId;
	@BusinessKey
	private int stars;
	@BusinessKey
	private String title;
	@BusinessKey
	private String review;
	
	@Override
    public String toString() { return BusinessIdentity.toString(this); }

    @Override
    public boolean equals(final Object o) { return BusinessIdentity.areEqual(this, o); }

    @Override
    public int hashCode() { return BusinessIdentity.getHashCode(this); }
}
