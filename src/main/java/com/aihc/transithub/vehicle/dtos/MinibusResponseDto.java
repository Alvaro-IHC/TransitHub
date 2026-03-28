package com.aihc.transithub.vehicle.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String type;
    private String brand;
    private Integer capacity;
    private String licensePlate;
    private String groupName;
    private UUID driverId;
}
