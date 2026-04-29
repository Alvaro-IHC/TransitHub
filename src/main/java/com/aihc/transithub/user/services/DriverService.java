package com.aihc.transithub.user.services;

import com.aihc.transithub.user.dtos.DriverCreateDto;
import com.aihc.transithub.user.dtos.DriverResponseDto;
import com.aihc.transithub.user.dtos.DriverUpdateDto;
import com.aihc.transithub.user.entities.Driver;
import com.aihc.transithub.user.repositories.DriverRepository;
import com.aihc.transithub.vehicle.entities.Vehicle;
import com.aihc.transithub.vehicle.repositories.VehicleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing driver operations in the system.
 *
 * @author Alvaro Huanca
 */
@Service
public class DriverService extends UserService {

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    /**
     * Create a new driver
     */
    public DriverResponseDto createDriver(DriverCreateDto driverCreateDto) {
        validateDriverCreate(driverCreateDto);
        
        Driver driver = new Driver();
        driver.setUsername(driverCreateDto.getUsername());
        driver.setPassword(driverCreateDto.getPassword());
        driver.setName(driverCreateDto.getName());
        driver.setLastName(driverCreateDto.getLastName());
        driver.setEmail(driverCreateDto.getEmail());
        driver.setPhone(driverCreateDto.getPhone());
        driver.setPhotoUrl(driverCreateDto.getPhotoUrl());
        driver.setDrivingLicense(driverCreateDto.getDrivingLicense());
        driver.setCategory(driverCreateDto.getCategory());
        driver.setType(driverCreateDto.getType());
        driver.setVehicles(driverCreateDto.getVehicleIds() != null
                ? getVehicles(driverCreateDto.getVehicleIds(), driver)
                : null);
        
        Driver savedDriver = driverRepository.save(driver);
        return mapToResponseDto(savedDriver);
    }

    private List<Vehicle> getVehicles(List<UUID> vehicleIds, Driver driver) {
        return vehicleIds.stream()
                .map(vehicleId -> {
                    Vehicle vehicle = vehicleRepository.findById(vehicleId)
                            .orElseThrow(() -> new IllegalArgumentException("Vehicle not found with ID: " + vehicleId));
                    vehicle.setDriver(driver);
                    return vehicle;
                })
                .collect(Collectors.toList());
    }

    /**
     * Get a driver by id
     */
    public DriverResponseDto getDriverById(UUID id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found, ID: " + id));
        return mapToResponseDto(driver);
    }

    /**
     * Get a driver by username
     */
    public DriverResponseDto getDriverByUsername(String username) {
        Driver driver = driverRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found, username: " + username));
        return mapToResponseDto(driver);
    }

    /**
     * Get all drivers
     */
    public List<DriverResponseDto> getAllDrivers() {
        return driverRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Update a driver
     */
    public DriverResponseDto updateDriver(UUID id, DriverUpdateDto driverUpdateDto) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found, ID: " + id));
        
        if (driverUpdateDto.getName() != null) {
            driver.setName(driverUpdateDto.getName());
        }
        if (driverUpdateDto.getLastName() != null) {
            driver.setLastName(driverUpdateDto.getLastName());
        }
        if (driverUpdateDto.getEmail() != null) {
            driver.setEmail(driverUpdateDto.getEmail());
        }
        if (driverUpdateDto.getPhone() != null) {
            driver.setPhone(driverUpdateDto.getPhone());
        }
        if (driverUpdateDto.getPhotoUrl() != null) {
            driver.setPhotoUrl(driverUpdateDto.getPhotoUrl());
        }
        if (driverUpdateDto.getDrivingLicense() != null) {
            driver.setDrivingLicense(driverUpdateDto.getDrivingLicense());
        }
        if (driverUpdateDto.getCategory() != null) {
            driver.setCategory(driverUpdateDto.getCategory());
        }
        if (driverUpdateDto.getType() != null) {
            driver.setType(driverUpdateDto.getType());
        }
        if (driverUpdateDto.getVehicleIds() != null) {
            driver.setVehicles(getVehicles(driverUpdateDto.getVehicleIds(), driver));
        }
        
        Driver updatedDriver = driverRepository.save(driver);
        return mapToResponseDto(updatedDriver);
    }

    /**
     * Delete a driver by id.
     */
    public void deleteDriver(UUID id) {
        Driver driver = driverRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Driver not found, ID: " + id));
        
        // Orphan the vehicles by setting their driver to null
        if (driver.getVehicles() != null) {
            driver.getVehicles().forEach(vehicle -> {
                vehicle.setDriver(null);
                vehicleRepository.save(vehicle);
            });
        }
        
        driverRepository.deleteById(id);
    }

    /**
     * Validates driver data
     */
    private void validateDriverCreate(DriverCreateDto driverCreateDto) {
        validateUserCreate(driverCreateDto);
        
        if (StringUtils.isBlank(driverCreateDto.getDrivingLicense())) {
            throw new IllegalArgumentException("Driving license is required");
        }
        
        if (driverRepository.existsByDrivingLicense(driverCreateDto.getDrivingLicense())) {
            throw new IllegalArgumentException("Driving license is already registered: " + driverCreateDto.getDrivingLicense());
        }
    }

    /**
     * Maps Driver entity to DriverResponseDto entity.
     */
    private DriverResponseDto mapToResponseDto(Driver driver) {
        return DriverResponseDto.builder()
                .id(driver.getId())
                .username(driver.getUsername())
                .name(driver.getName())
                .lastName(driver.getLastName())
                .email(driver.getEmail())
                .phone(driver.getPhone())
                .photoUrl(driver.getPhotoUrl())
                .drivingLicense(driver.getDrivingLicense())
                .category(driver.getCategory())
                .type(driver.getType())
                .vehicleIds(driver.getVehicles() != null
                        ? driver.getVehicles().stream().map(Vehicle::getId).collect(Collectors.toList())
                        : null)
                .build();
    }
}
