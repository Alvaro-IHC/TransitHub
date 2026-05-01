package com.aihc.transithub.vehicle.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.aihc.transithub.vehicle.entities.MinibusStatus;

import java.util.UUID;

/**
 * Data Transfer Object for minibus response in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MinibusResponseDto {
    private UUID id;
    private String ruat;
    private String model;
    private String brand;
    private Integer capacity;
    private String licensePlate;
    private String groupName;
    private UUID driverId;
    private MinibusStatus status;
    private String photoUrl;
}
