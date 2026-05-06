package com.aihc.transithub.vehicle.services;

import com.aihc.transithub.finance.entities.Contribution;
import com.aihc.transithub.user.entities.Driver;
import com.aihc.transithub.user.repositories.DriverRepository;
import com.aihc.transithub.vehicle.dtos.VehicleCreateDto;
import com.aihc.transithub.vehicle.dtos.VehicleResponseDto;
import com.aihc.transithub.vehicle.dtos.VehicleUpdateDto;
import com.aihc.transithub.vehicle.entities.DumpTruck;
import com.aihc.transithub.vehicle.entities.Minibus;
import com.aihc.transithub.vehicle.entities.Vehicle;
import com.aihc.transithub.vehicle.entities.VehicleType;
import com.aihc.transithub.vehicle.repositories.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing vehicle operations in the system.
 *
 * @author Alvaro Huanca
 */
@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private DriverRepository driverRepository;

    /**
     * Create a new vehicle based on type
     */
    public VehicleResponseDto createVehicle(VehicleCreateDto vehicleCreateDto) {
        validateVehicleCreate(vehicleCreateDto);

        Vehicle vehicle;
        if (vehicleCreateDto.getType() == VehicleType.MINIBUS) {
            vehicle = new Minibus();
        } else if (vehicleCreateDto.getType() == VehicleType.DUMP_TRUCK) {
            vehicle = new DumpTruck();
        } else {
            throw new IllegalArgumentException("Invalid vehicle type");
        }

        vehicle.setRuat(vehicleCreateDto.getRuat());
        vehicle.setModel(vehicleCreateDto.getModel());
        vehicle.setBrand(vehicleCreateDto.getBrand());
        vehicle.setCapacity(vehicleCreateDto.getCapacity());
        vehicle.setLicensePlate(vehicleCreateDto.getLicensePlate());
        vehicle.setGroupName(vehicleCreateDto.getGroupName());
        vehicle.setPhotoUrl(vehicleCreateDto.getPhotoUrl());
        vehicle.setAffiliationDate(vehicleCreateDto.getAffiliationDate());

        if (vehicleCreateDto.getDriverId() != null) {
            Driver driver = driverRepository.findById(vehicleCreateDto.getDriverId())
                    .orElseThrow(() -> new IllegalArgumentException("Driver not found with ID: " + vehicleCreateDto.getDriverId()));
            vehicle.setDriver(driver);
        }

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return mapToResponseDto(savedVehicle);
    }

    /**
     * Get a vehicle by ID
     */
    public VehicleResponseDto getVehicleById(UUID id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with ID: " + id));
        return mapToResponseDto(vehicle);
    }

    /**
     * Get all vehicles with optional driver filter
     */
    public List<VehicleResponseDto> getAllVehicles(Boolean hasDriver) {
        List<Vehicle> vehicles = vehicleRepository.findAll();

        if (hasDriver != null) {
            vehicles = vehicles.stream()
                    .filter(vehicle -> hasDriver == (vehicle.getDriver() != null))
                    .toList();
        }

        return vehicles.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Update a vehicle
     */
    public VehicleResponseDto updateVehicle(UUID id, VehicleUpdateDto vehicleUpdateDto) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with ID: " + id));

        if (vehicleUpdateDto.getRuat() != null) {
            vehicle.setRuat(vehicleUpdateDto.getRuat());
        }
        if (vehicleUpdateDto.getModel() != null) {
            vehicle.setModel(vehicleUpdateDto.getModel());
        }
        if (vehicleUpdateDto.getBrand() != null) {
            vehicle.setBrand(vehicleUpdateDto.getBrand());
        }
        if (vehicleUpdateDto.getCapacity() != null) {
            vehicle.setCapacity(vehicleUpdateDto.getCapacity());
        }
        if (vehicleUpdateDto.getLicensePlate() != null) {
            vehicle.setLicensePlate(vehicleUpdateDto.getLicensePlate());
        }
        if (vehicleUpdateDto.getGroupName() != null) {
            vehicle.setGroupName(vehicleUpdateDto.getGroupName());
        }
        if (vehicleUpdateDto.getDriverId() != null) {
            Driver driver = driverRepository.findById(vehicleUpdateDto.getDriverId())
                    .orElseThrow(() -> new IllegalArgumentException("Driver not found with ID: " + vehicleUpdateDto.getDriverId()));
            vehicle.setDriver(driver);
        }
        if (vehicleUpdateDto.getPhotoUrl() != null) {
            vehicle.setPhotoUrl(vehicleUpdateDto.getPhotoUrl());
        }
        if (vehicleUpdateDto.getAffiliationDate() != null) {
            vehicle.setAffiliationDate(vehicleUpdateDto.getAffiliationDate());
        }

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return mapToResponseDto(updatedVehicle);
    }

    /**
     * Delete a vehicle by ID
     */
    public void deleteVehicle(UUID id) {
        if (!vehicleRepository.existsById(id)) {
            throw new IllegalArgumentException("Vehicle not found with ID: " + id);
        }
        vehicleRepository.deleteById(id);
    }

    /**
     * Validate data for creating vehicle
     */
    private void validateVehicleCreate(VehicleCreateDto vehicleCreateDto) {
        if (vehicleCreateDto.getLicensePlate() == null || vehicleCreateDto.getLicensePlate().trim().isEmpty()) {
            throw new IllegalArgumentException("License plate is required");
        }
        if (vehicleCreateDto.getType() == null) {
            throw new IllegalArgumentException("Vehicle type is required");
        }
        if (vehicleCreateDto.getAffiliationDate() == null) {
            throw new IllegalArgumentException("Affiliation date is required");
        }

        // Check if license plate already exists
        if (vehicleRepository.findAll().stream().anyMatch(v -> vehicleCreateDto.getLicensePlate().equals(v.getLicensePlate()))) {
            throw new IllegalArgumentException("License plate already exists: " + vehicleCreateDto.getLicensePlate());
        }
    }

    /**
     * Map Vehicle entity to VehicleResponseDto
     */
    private VehicleResponseDto mapToResponseDto(Vehicle vehicle) {
        VehicleType type;
        if (vehicle instanceof Minibus) {
            type = VehicleType.MINIBUS;
        } else if (vehicle instanceof DumpTruck) {
            type = VehicleType.DUMP_TRUCK;
        } else {
            throw new IllegalStateException("Unknown vehicle type");
        }

        return VehicleResponseDto.builder()
                .id(vehicle.getId())
                .ruat(vehicle.getRuat())
                .model(vehicle.getModel())
                .brand(vehicle.getBrand())
                .capacity(vehicle.getCapacity())
                .licensePlate(vehicle.getLicensePlate())
                .groupName(vehicle.getGroupName())
                .driverId(vehicle.getDriver() != null ? vehicle.getDriver().getId() : null)
                .type(type)
                .photoUrl(vehicle.getPhotoUrl())
                .affiliationDate(vehicle.getAffiliationDate())
                .build();
    }

    public List<YearMonth> getVehicleDebts(UUID id) {

        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with ID: " + id));
        YearMonth start = vehicle.getAffiliationYearMonth();
        YearMonth end = YearMonth.now();

        List<YearMonth> payments = vehicle.getYearMonths();
        List<YearMonth> missingMonths = new ArrayList<>();
        YearMonth current = start;

        while (!current.isAfter(end)) {
            if (!payments.contains(current)) {
                missingMonths.add(current);
            }
            current = current.plusMonths(1);
        }

        return missingMonths;
    }
}
