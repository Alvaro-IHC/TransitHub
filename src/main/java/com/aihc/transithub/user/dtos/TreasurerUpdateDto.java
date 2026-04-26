package com.aihc.transithub.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO to update a treasurer instance.
 *
 * @author Alvaro Huanca
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TreasurerUpdateDto extends UserUpdateDto {
    private String officeNumber;
    private LocalDate positionStartDate;
    private String bankAccount;
}
