package com.aihc.transithub.vehicle.services;

import com.aihc.transithub.user.entities.Driver;
import com.aihc.transithub.user.repositories.DriverRepository;
import com.aihc.transithub.vehicle.dtos.MinibusCreateDto;
import com.aihc.transithub.vehicle.dtos.MinibusResponseDto;
import com.aihc.transithub.vehicle.dtos.MinibusUpdateDto;
import com.aihc.transithub.vehicle.entities.Minibus;
import com.aihc.transithub.vehicle.repositories.MinibusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing minibus operations in the system.
 *
 * @author Alvaro Huanca
 */
@Service
public class MinibusService {

    @Autowired
    private MinibusRepository minibusRepository;

    @Autowired
    private DriverRepository driverRepository;

    /**
     * Create a new minibus
     */
    public MinibusResponseDto createMinibus(MinibusCreateDto minibusCreateDto) {
        validateMinibusCreate(minibusCreateDto);

        Minibus minibus = new Minibus();
        minibus.setRuat(minibusCreateDto.getRuat());
        minibus.setModel(minibusCreateDto.getModel());
        minibus.setType(minibusCreateDto.getType());
        minibus.setBrand(minibusCreateDto.getBrand());
        minibus.setCapacity(minibusCreateDto.getCapacity());
        minibus.setLicensePlate(minibusCreateDto.getLicensePlate());
        minibus.setGroupName(minibusCreateDto.getGroupName());

        if (minibusCreateDto.getDriverId() != null) {
            Driver driver = driverRepository.findById(minibusCreateDto.getDriverId())
                    .orElseThrow(() -> new IllegalArgumentException("Driver not found with ID: " + minibusCreateDto.getDriverId()));
            minibus.setDriver(driver);
        }

        Minibus savedMinibus = minibusRepository.save(minibus);
        return mapToResponseDto(savedMinibus);
    }

    /**
     * Get a minibus by ID
     */
    public MinibusResponseDto getMinibusById(UUID id) {
        Minibus minibus = minibusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Minibus not found with ID: " + id));
        return mapToResponseDto(minibus);
    }

    /**
     * Get all minibuses
     */
    public List<MinibusResponseDto> getAllMinibuses() {
        return minibusRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Update a minibus
     */
    public MinibusResponseDto updateMinibus(UUID id, MinibusUpdateDto minibusUpdateDto) {
        Minibus minibus = minibusRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Minibus not found with ID: " + id));

        if (minibusUpdateDto.getRuat() != null) {
            minibus.setRuat(minibusUpdateDto.getRuat());
        }
        if (minibusUpdateDto.getModel() != null) {
            minibus.setModel(minibusUpdateDto.getModel());
        }
        if (minibusUpdateDto.getType() != null) {
            minibus.setType(minibusUpdateDto.getType());
        }
        if (minibusUpdateDto.getBrand() != null) {
            minibus.setBrand(minibusUpdateDto.getBrand());
        }
        if (minibusUpdateDto.getCapacity() != null) {
            minibus.setCapacity(minibusUpdateDto.getCapacity());
        }
        if (minibusUpdateDto.getLicensePlate() != null) {
            minibus.setLicensePlate(minibusUpdateDto.getLicensePlate());
        }
        if (minibusUpdateDto.getGroupName() != null) {
            minibus.setGroupName(minibusUpdateDto.getGroupName());
        }
        if (minibusUpdateDto.getDriverId() != null) {
            Driver driver = driverRepository.findById(minibusUpdateDto.getDriverId())
                    .orElseThrow(() -> new IllegalArgumentException("Driver not found with ID: " + minibusUpdateDto.getDriverId()));
            minibus.setDriver(driver);
        }

        Minibus updatedMinibus = minibusRepository.save(minibus);
        return mapToResponseDto(updatedMinibus);
    }

    /**
     * Delete a minibus by ID
     */
    public void deleteMinibus(UUID id) {
        if (!minibusRepository.existsById(id)) {
            throw new IllegalArgumentException("Minibus not found with ID: " + id);
        }
        minibusRepository.deleteById(id);
    }

    /**
     * Validate data for creating minibus
     */
    private void validateMinibusCreate(MinibusCreateDto minibusCreateDto) {
        if (minibusCreateDto.getLicensePlate() == null || minibusCreateDto.getLicensePlate().trim().isEmpty()) {
            throw new IllegalArgumentException("License plate is required");
        }

        // Check if license plate already exists
        if (minibusRepository.findAll().stream().anyMatch(m -> minibusCreateDto.getLicensePlate().equals(m.getLicensePlate()))) {
            throw new IllegalArgumentException("License plate already exists: " + minibusCreateDto.getLicensePlate());
        }
    }

    /**
     * Map Minibus entity to MinibusResponseDto
     */
    private MinibusResponseDto mapToResponseDto(Minibus minibus) {
        return MinibusResponseDto.builder()
                .id(minibus.getId())
                .ruat(minibus.getRuat())
                .model(minibus.getModel())
                .type(minibus.getType())
                .brand(minibus.getBrand())
                .capacity(minibus.getCapacity())
                .licensePlate(minibus.getLicensePlate())
                .groupName(minibus.getGroupName())
                .driverId(minibus.getDriver() != null ? minibus.getDriver().getId() : null)
                .build();
    }
}
