package com.aihc.transithub.auth.dtos;

import com.aihc.transithub.user.dtos.UserWithRoleDto;

/**
 * Login response DTO containing JWT token and user information.
 *
 * @author Alvaro Huanca
 */
public record LoginResponseDto(
        String token,
        String message,
        UserWithRoleDto user
) {}

