package com.aihc.transithub.travel.controllers;

import com.aihc.transithub.travel.dtos.ParcelCreateDto;
import com.aihc.transithub.travel.dtos.ParcelResponseDto;
import com.aihc.transithub.travel.dtos.ParcelUpdateDto;
import com.aihc.transithub.travel.services.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing parcel operations in the system.
 *
 * @author Alvaro Huanca
 */
@RestController
@RequestMapping("/api/parcels")
public class ParcelController {

    @Autowired
    private ParcelService parcelService;

    /**
     * POST: Create a new parcel
     * Endpoint: POST /api/parcels
     */
    @PostMapping
    public ResponseEntity<ParcelResponseDto> createParcel(@RequestBody ParcelCreateDto parcelCreateDto) {
        try {
            ParcelResponseDto parcelResponseDto = parcelService.createParcel(parcelCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(parcelResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET: Get a parcel by ID
     * Endpoint: GET /api/parcels/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ParcelResponseDto> getParcelById(@PathVariable UUID id) {
        try {
            ParcelResponseDto parcelResponseDto = parcelService.getParcelById(id);
            return ResponseEntity.ok(parcelResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get all parcels or filter by trip or agent
     * Endpoint: GET /api/parcels or GET /api/parcels?tripId=xxx or GET /api/parcels?agentId=xxx
     */
    @GetMapping
    public ResponseEntity<List<ParcelResponseDto>> getParcels(
            @RequestParam(name = "tripId", required = false) UUID tripId,
            @RequestParam(name = "agentId", required = false) UUID agentId) {
        List<ParcelResponseDto> parcels = parcelService.getParcels(tripId, agentId);
        return ResponseEntity.ok(parcels);
    }

    /**
     * PUT: Update a parcel
     * Endpoint: PUT /api/parcels/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ParcelResponseDto> updateParcel(
            @PathVariable UUID id,
            @RequestBody ParcelUpdateDto parcelUpdateDto) {
        try {
            ParcelResponseDto parcelResponseDto = parcelService.updateParcel(id, parcelUpdateDto);
            return ResponseEntity.ok(parcelResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE: Delete a parcel
     * Endpoint: DELETE /api/parcels/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteParcel(@PathVariable UUID id) {
        try {
            parcelService.deleteParcel(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}

