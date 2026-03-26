package com.aihc.transithub.user.controllers;

import com.aihc.transithub.user.dtos.DriverCreateDto;
import com.aihc.transithub.user.dtos.DriverResponseDto;
import com.aihc.transithub.user.dtos.DriverUpdateDto;
import com.aihc.transithub.user.services.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing driver operations in the system.
 *
 * @author Alvaro Huanca
 */
@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    /**
     * POST: Create a new driver
     * Endpoint: POST /api/drivers
     */
    @PostMapping
    public ResponseEntity<DriverResponseDto> createDriver(@RequestBody DriverCreateDto driverCreateDto) {
        try {
            DriverResponseDto driverResponseDto = driverService.createDriver(driverCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(driverResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET: Get a driver by ID
     * Endpoint: GET /api/drivers/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<DriverResponseDto> getDriverById(@PathVariable UUID id) {
        try {
            DriverResponseDto driverResponseDto = driverService.getDriverById(id);
            return ResponseEntity.ok(driverResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get a driver by username
     * Endpoint: GET /api/drivers/username/{username}
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<DriverResponseDto> getDriverByUsername(@PathVariable String username) {
        try {
            DriverResponseDto driverResponseDto = driverService.getDriverByUsername(username);
            return ResponseEntity.ok(driverResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get all drivers
     * Endpoint: GET /api/drivers
     */
    @GetMapping
    public ResponseEntity<List<DriverResponseDto>> getAllDrivers() {
        List<DriverResponseDto> drivers = driverService.getAllDrivers();
        return ResponseEntity.ok(drivers);
    }

    /**
     * PUT: Update a driver
     * Endpoint: PUT /api/drivers/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<DriverResponseDto> updateDriver(
            @PathVariable UUID id,
            @RequestBody DriverUpdateDto driverUpdateDto) {
        try {
            DriverResponseDto driverResponseDto = driverService.updateDriver(id, driverUpdateDto);
            return ResponseEntity.ok(driverResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE: Delete a driver
     * Endpoint: DELETE /api/drivers/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable UUID id) {
        try {
            driverService.deleteDriver(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
