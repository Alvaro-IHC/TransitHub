package com.aihc.transithub.travel.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object for creating a new trip in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripCreateDto {
    private String destination;
    private LocalDate date;
    private String time;
    private String driverName;
    private UUID minibusId;
}
