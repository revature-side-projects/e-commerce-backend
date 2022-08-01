package com.revature.dtos;

import com.openpojo.business.BusinessIdentity;
import com.openpojo.business.annotation.BusinessKey;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class UserRequest {

	private Integer id;
	@NonNull
	@BusinessKey
	private String email;
	@BusinessKey
	private String password;
	@NonNull
	@BusinessKey
	private String firstName;
	@NonNull
	@BusinessKey
	private String lastName;
	@BusinessKey
	private String role;
	
	@Override
    public String toString() { return BusinessIdentity.toString(this); }

    @Override
    public boolean equals(final Object o) { return BusinessIdentity.areEqual(this, o); }

    @Override
    public int hashCode() { return BusinessIdentity.getHashCode(this); }
}
