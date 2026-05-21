package com.aihc.transithub.auth.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Login request DTO for user authentication.
 * Accepts either username or email along with password.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    private String usernameOrEmail;
    private String password;
}


