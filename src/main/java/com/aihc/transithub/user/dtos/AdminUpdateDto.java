package com.aihc.transithub.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO to update an admin instance.
 *
 * @author Alvaro Huanca
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUpdateDto extends UserUpdateDto {
    private String position;
    private LocalDate positionStartDate;
    private String accessLevel;
}
