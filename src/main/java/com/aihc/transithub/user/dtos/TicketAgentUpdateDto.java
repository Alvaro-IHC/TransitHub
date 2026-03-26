package com.aihc.transithub.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DTO to update a ticket agent instance.
 *
 * @author Alvaro Huanca
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketAgentUpdateDto extends UserUpdateDto {
    private String counterNumber;
    private String terminalName;
    private LocalTime shiftStart;
    private LocalTime shiftEnd;
    private LocalDate positionStartDate;
}

