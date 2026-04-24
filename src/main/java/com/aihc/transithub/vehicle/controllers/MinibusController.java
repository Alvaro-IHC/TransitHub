package com.aihc.transithub.vehicle.controllers;

import com.aihc.transithub.vehicle.dtos.MinibusCreateDto;
import com.aihc.transithub.vehicle.dtos.MinibusResponseDto;
import com.aihc.transithub.vehicle.dtos.MinibusUpdateDto;
import com.aihc.transithub.vehicle.entities.MinibusStatus;
import com.aihc.transithub.vehicle.services.MinibusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing minibus operations in the system.
 *
 * @author Alvaro Huanca
 */
@RestController
@RequestMapping("/api/minibuses")
public class MinibusController {

    @Autowired
    private MinibusService minibusService;

    /**
     * POST: Create a new minibus
     * Endpoint: POST /api/minibuses
     */
    @PostMapping
    public ResponseEntity<MinibusResponseDto> createMinibus(@RequestBody MinibusCreateDto minibusCreateDto) {
        try {
            MinibusResponseDto minibusResponseDto = minibusService.createMinibus(minibusCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(minibusResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET: Get a minibus by ID
     * Endpoint: GET /api/minibuses/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<MinibusResponseDto> getMinibusById(@PathVariable UUID id) {
        try {
            MinibusResponseDto minibusResponseDto = minibusService.getMinibusById(id);
            return ResponseEntity.ok(minibusResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get all minibuses or filter by status
     * Endpoint: GET /api/minibuses?status=ACTIVE or GET /api/minibuses?status=INACTIVE or GET /api/minibuses (all)
     */
    @GetMapping
    public ResponseEntity<List<MinibusResponseDto>> getAllMinibuses(@RequestParam(required = false) MinibusStatus status) {
        List<MinibusResponseDto> minibuses;
        if (status != null) {
            minibuses = minibusService.getMinibusesByStatus(status);
        } else {
            minibuses = minibusService.getAllMinibuses();
        }
        return ResponseEntity.ok(minibuses);
    }

    /**
     * PUT: Update a minibus
     * Endpoint: PUT /api/minibuses/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<MinibusResponseDto> updateMinibus(
            @PathVariable UUID id,
            @RequestBody MinibusUpdateDto minibusUpdateDto) {
        try {
            MinibusResponseDto minibusResponseDto = minibusService.updateMinibus(id, minibusUpdateDto);
            return ResponseEntity.ok(minibusResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE: Delete a minibus
     * Endpoint: DELETE /api/minibuses/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMinibus(@PathVariable UUID id) {
        try {
            minibusService.deleteMinibus(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
