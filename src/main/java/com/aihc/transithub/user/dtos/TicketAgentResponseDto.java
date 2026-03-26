package com.aihc.transithub.user.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

/**
 * DTO response that represents a ticket agent instance.
 *
 * @author Alvaro Huanca
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class TicketAgentResponseDto extends UserResponseDto {
    private String counterNumber;
    private String terminalName;
    private String shiftStart;
    private String shiftEnd;
    private LocalDate positionStartDate;
}
