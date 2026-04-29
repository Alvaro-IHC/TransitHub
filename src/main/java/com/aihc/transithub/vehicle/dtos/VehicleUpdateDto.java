package com.aihc.transithub.vehicle.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Data Transfer Object for updating a vehicle in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VehicleUpdateDto {
    private String ruat;
    private String model;
    private String brand;
    private Integer capacity;
    private String licensePlate;
    private String groupName;
    private UUID driverId;
    private String photoUrl;
}
