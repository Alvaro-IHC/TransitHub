package com.aihc.transithub.travel.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object for creating a new ticket in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketCreateDto {
    private String passengerName;
    private Integer seatNumber;
    private BigDecimal cost;
    private String destination;
    private LocalDate date;
    private UUID tripId;
    private UUID agentId;
}
