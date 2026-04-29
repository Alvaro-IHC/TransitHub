package com.aihc.transithub.user.dtos;

import com.aihc.transithub.user.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * DTO to represent a user with their role.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWithRoleDto {
    private UUID id;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private String photoUrl;
}
