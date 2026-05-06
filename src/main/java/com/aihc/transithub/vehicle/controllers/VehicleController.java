package com.aihc.transithub.vehicle.controllers;

import com.aihc.transithub.vehicle.dtos.VehicleCreateDto;
import com.aihc.transithub.vehicle.dtos.VehicleResponseDto;
import com.aihc.transithub.vehicle.dtos.VehicleUpdateDto;
import com.aihc.transithub.vehicle.services.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.YearMonth;
import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing vehicle operations in the system.
 *
 * @author Alvaro Huanca
 */
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    /**
     * POST: Create a new vehicle
     * Endpoint: POST /api/vehicles
     */
    @PostMapping
    public ResponseEntity<VehicleResponseDto> createVehicle(@RequestBody VehicleCreateDto vehicleCreateDto) {
        try {
            VehicleResponseDto vehicleResponseDto = vehicleService.createVehicle(vehicleCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(vehicleResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET: Get a vehicle by ID
     * Endpoint: GET /api/vehicles/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> getVehicleById(@PathVariable UUID id) {
        try {
            VehicleResponseDto vehicleResponseDto = vehicleService.getVehicleById(id);
            return ResponseEntity.ok(vehicleResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get all vehicles with optional driver filter
     * Endpoint: GET /api/vehicles?hasDriver=true (with driver), ?hasDriver=false (without driver), or no param (all)
     */
    @GetMapping
    public ResponseEntity<List<VehicleResponseDto>> getAllVehicles(@RequestParam(required = false) Boolean hasDriver) {
        List<VehicleResponseDto> vehicles = vehicleService.getAllVehicles(hasDriver);
        return ResponseEntity.ok(vehicles);
    }

    /**
     * PUT: Update a vehicle
     * Endpoint: PUT /api/vehicles/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponseDto> updateVehicle(
            @PathVariable UUID id,
            @RequestBody VehicleUpdateDto vehicleUpdateDto) {
        try {
            VehicleResponseDto vehicleResponseDto = vehicleService.updateVehicle(id, vehicleUpdateDto);
            return ResponseEntity.ok(vehicleResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE: Delete a vehicle
     * Endpoint: DELETE /api/vehicles/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@PathVariable UUID id) {
        try {
            vehicleService.deleteVehicle(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get debts of a vehicle
     * Endpoint: GET /api/vehicles/{id}/debts
     */
    @GetMapping("/{id}/debts")
    public ResponseEntity<List<YearMonth>> getVehicleDebts(@PathVariable UUID id) {
        List<YearMonth> debts = vehicleService.getVehicleDebts(id);
        return ResponseEntity.ok(debts);
    }
}
