package com.aihc.transithub.vehicle.dtos;

import com.aihc.transithub.vehicle.entities.VehicleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Data Transfer Object for vehicle response in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleResponseDto {
    private UUID id;
    private String ruat;
    private String model;
    private String brand;
    private Integer capacity;
    private String licensePlate;
    private String groupName;
    private UUID driverId;
    private VehicleType type;
    private String photoUrl;
}
