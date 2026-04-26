package com.aihc.transithub.user.controllers;

import com.aihc.transithub.user.dtos.TreasurerCreateDto;
import com.aihc.transithub.user.dtos.TreasurerResponseDto;
import com.aihc.transithub.user.dtos.TreasurerUpdateDto;
import com.aihc.transithub.user.services.TreasurerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing treasurer operations in the system.
 *
 * @author Alvaro Huanca
 */
@RestController
@RequestMapping("/api/treasurers")
public class TreasurerController {

    @Autowired
    private TreasurerService treasurerService;

    /**
     * POST: Create a new treasurer
     * Endpoint: POST /api/treasurers
     */
    @PostMapping
    public ResponseEntity<TreasurerResponseDto> createTreasurer(@RequestBody TreasurerCreateDto treasurerCreateDto) {
        try {
            TreasurerResponseDto treasurerResponseDto = treasurerService.createTreasurer(treasurerCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(treasurerResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET: Get a treasurer by ID
     * Endpoint: GET /api/treasurers/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TreasurerResponseDto> getTreasurerById(@PathVariable UUID id) {
        try {
            TreasurerResponseDto treasurerResponseDto = treasurerService.getTreasurerById(id);
            return ResponseEntity.ok(treasurerResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get a treasurer by username
     * Endpoint: GET /api/treasurers/username/{username}
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<TreasurerResponseDto> getTreasurerByUsername(@PathVariable String username) {
        try {
            TreasurerResponseDto treasurerResponseDto = treasurerService.getTreasurerByUsername(username);
            return ResponseEntity.ok(treasurerResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get all treasurers
     * Endpoint: GET /api/treasurers
     */
    @GetMapping
    public ResponseEntity<List<TreasurerResponseDto>> getAllTreasurers() {
        List<TreasurerResponseDto> treasurers = treasurerService.getAllTreasurers();
        return ResponseEntity.ok(treasurers);
    }

    /**
     * PUT: Update a treasurer
     * Endpoint: PUT /api/treasurers/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TreasurerResponseDto> updateTreasurer(@PathVariable UUID id, @RequestBody TreasurerUpdateDto treasurerUpdateDto) {
        try {
            TreasurerResponseDto treasurerResponseDto = treasurerService.updateTreasurer(id, treasurerUpdateDto);
            return ResponseEntity.ok(treasurerResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE: Delete a treasurer by ID
     * Endpoint: DELETE /api/treasurers/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTreasurer(@PathVariable UUID id) {
        try {
            treasurerService.deleteTreasurer(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
