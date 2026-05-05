package com.aihc.transithub.travel.dtos;

import com.aihc.transithub.travel.enums.TripStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object for trip response in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TripResponseDto {
    private UUID id;
    private String destination;
    private LocalDate date;
    private String time;
    private String driverName;
    private TripStatus status;
    private UUID minibusId;
    private UUID agentId;
}
