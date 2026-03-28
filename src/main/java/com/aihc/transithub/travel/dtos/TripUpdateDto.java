package com.aihc.transithub.travel.dtos;

import com.aihc.transithub.travel.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object for updating a trip in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TripUpdateDto {
    private String destination;
    private LocalDate date;
    private String time;
    private String driverName;
    private TripStatus status;
    private UUID minibusId;
}
