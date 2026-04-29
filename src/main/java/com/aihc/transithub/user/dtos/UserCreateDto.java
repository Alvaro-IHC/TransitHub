package com.aihc.transithub.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for creating a new user in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDto {
    private String username;
    private String password;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String photoUrl;
}
