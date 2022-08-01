package com.revature.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class UserRequest {

	private Integer id;
	@NonNull
	private String email;
	private String password;
	@NonNull
	private String firstName;
	@NonNull
	private String lastName;
	private String role;
}
