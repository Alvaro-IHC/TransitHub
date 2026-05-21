package com.aihc.transithub.auth.dtos;

/**
 * Error response DTO for authentication failures.
 *
 * @author Alvaro Huanca
 */
public record ErrorResponseDto(
        int status,
        String message
) {}
