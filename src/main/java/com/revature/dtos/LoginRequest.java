package com.revature.dtos;

import com.revature.utils.RegexUtil;
import com.revature.utils.ValidatorMessages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotNull( message = ValidatorMessages.EMAIL_REQUIRED)
    @Email( message = ValidatorMessages.EMAIL_REQUIREMENTS)
    private String email;

    @NotNull( message = ValidatorMessages.PASSWORD_REQUIRED)
    @Pattern( regexp = RegexUtil.PASSWORD, message = ValidatorMessages.PASSWORD_REQUIREMENTS)
    private String password;
}
