package com.aihc.transithub.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * DTO to create a ticket agent instance.
 *
 * @author Alvaro Huanca
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketAgentCreateDto extends UserCreateDto {
    private LocalDate positionStartDate;
    private String terminalName;
//    private LocalTime shiftEnd;
//    private LocalTime shiftStart;
//    private String counterNumber;
}

