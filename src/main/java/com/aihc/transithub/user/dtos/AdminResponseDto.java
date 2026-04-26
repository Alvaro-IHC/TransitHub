package com.aihc.transithub.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO to represent an admin response.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminResponseDto {
    private UUID id;
    private String username;
    private String name;
    private String lastName;
    private String email;
    private String phone;
    private String position;
    private LocalDate positionStartDate;
    private String accessLevel;
}
