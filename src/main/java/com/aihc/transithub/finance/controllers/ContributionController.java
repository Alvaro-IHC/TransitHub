package com.aihc.transithub.finance.controllers;

import com.aihc.transithub.finance.dtos.ContributionCreateDto;
import com.aihc.transithub.finance.dtos.ContributionResponseDto;
import com.aihc.transithub.finance.dtos.ContributionUpdateDto;
import com.aihc.transithub.finance.services.ContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing contribution operations in the system.
 *
 * @author Alvaro Huanca
 */
@RestController
@RequestMapping("/api/contributions")
public class ContributionController {

    @Autowired
    private ContributionService contributionService;

    /**
     * POST: Create a new contribution
     * Endpoint: POST /api/contributions
     */
    @PostMapping
    public ResponseEntity<ContributionResponseDto> createContribution(@RequestBody ContributionCreateDto contributionCreateDto) {
        try {
            ContributionResponseDto contributionResponseDto = contributionService.createContribution(contributionCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(contributionResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET: Get a contribution by ID
     * Endpoint: GET /api/contributions/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContributionResponseDto> getContributionById(@PathVariable UUID id) {
        try {
            ContributionResponseDto contributionResponseDto = contributionService.getContributionById(id);
            return ResponseEntity.ok(contributionResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get all contributions
     * Endpoint: GET /api/contributions
     */
    @GetMapping
    public ResponseEntity<List<ContributionResponseDto>> getAllContributions() {
        List<ContributionResponseDto> contributions = contributionService.getAllContributions();
        return ResponseEntity.ok(contributions);
    }

    /**
     * GET: Get contributions by vehicle ID
     * Endpoint: GET /api/contributions/vehicle/{vehicleId}
     */
    @GetMapping("/vehicle/{vehicleId}")
    public ResponseEntity<List<ContributionResponseDto>> getContributionsByVehicleId(@PathVariable UUID vehicleId) {
        List<ContributionResponseDto> contributions = contributionService.getContributionsByVehicleId(vehicleId);
        return ResponseEntity.ok(contributions);
    }

    /**
     * GET: Get contributions by treasurer ID
     * Endpoint: GET /api/contributions/treasurer/{treasurerId}
     */
    @GetMapping("/treasurer/{treasurerId}")
    public ResponseEntity<List<ContributionResponseDto>> getContributionsByTreasurerId(@PathVariable UUID treasurerId) {
        List<ContributionResponseDto> contributions = contributionService.getContributionsByTreasurerId(treasurerId);
        return ResponseEntity.ok(contributions);
    }

    /**
     * PUT: Update a contribution
     * Endpoint: PUT /api/contributions/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContributionResponseDto> updateContribution(@PathVariable UUID id, @RequestBody ContributionUpdateDto contributionUpdateDto) {
        try {
            ContributionResponseDto contributionResponseDto = contributionService.updateContribution(id, contributionUpdateDto);
            return ResponseEntity.ok(contributionResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE: Delete a contribution by ID
     * Endpoint: DELETE /api/contributions/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContribution(@PathVariable UUID id) {
        try {
            contributionService.deleteContribution(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
