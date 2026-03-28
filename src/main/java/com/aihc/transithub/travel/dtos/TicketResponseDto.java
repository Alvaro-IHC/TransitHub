package com.aihc.transithub.travel.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object for ticket response in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponseDto {
    private UUID id;
    private String passengerName;
    private Integer seatNumber;
    private BigDecimal cost;
    private String destination;
    private LocalDate date;
    private UUID tripId;
    private UUID agentId;
}
