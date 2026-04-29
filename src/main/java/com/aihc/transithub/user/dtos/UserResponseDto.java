package com.aihc.transithub.user.dtos;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

/**
 * DTO response that represents a user instance.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserResponseDto {
    protected UUID id;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String photoUrl;
}
