package com.aihc.transithub.travel.controllers;

import com.aihc.transithub.travel.dtos.TripCreateDto;
import com.aihc.transithub.travel.dtos.TripResponseDto;
import com.aihc.transithub.travel.dtos.TripUpdateDto;
import com.aihc.transithub.travel.enums.TripStatus;
import com.aihc.transithub.travel.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing trip operations in the system.
 *
 * @author Alvaro Huanca
 */
@RestController
@RequestMapping("/api/trips")
public class TripController {

    @Autowired
    private TripService tripService;

    /**
     * POST: Create a new trip
     * Endpoint: POST /api/trips
     */
    @PostMapping
    public ResponseEntity<TripResponseDto> createTrip(@RequestBody TripCreateDto tripCreateDto) {
        try {
            TripResponseDto tripResponseDto = tripService.createTrip(tripCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(tripResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET: Get a trip by ID
     * Endpoint: GET /api/trips/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TripResponseDto> getTripById(@PathVariable UUID id) {
        try {
            TripResponseDto tripResponseDto = tripService.getTripById(id);
            return ResponseEntity.ok(tripResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get all trips or filter by status
     * Endpoint: GET /api/trips?status=NOT_DEPARTED or GET /api/trips?status=DEPARTED
     */
    @GetMapping
    public ResponseEntity<List<TripResponseDto>> getTrips(@RequestParam(required = false) TripStatus status) {
        try {
            List<TripResponseDto> trips;
            if (status != null) {
                trips = tripService.getTripsByStatus(status);
            } else {
                trips = tripService.getAllTrips();
            }
            return ResponseEntity.ok(trips);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT: Update a trip
     * Endpoint: PUT /api/trips/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TripResponseDto> updateTrip(
            @PathVariable UUID id,
            @RequestBody TripUpdateDto tripUpdateDto) {
        try {
            TripResponseDto tripResponseDto = tripService.updateTrip(id, tripUpdateDto);
            return ResponseEntity.ok(tripResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE: Delete a trip
     * Endpoint: DELETE /api/trips/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable UUID id) {
        try {
            tripService.deleteTrip(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
