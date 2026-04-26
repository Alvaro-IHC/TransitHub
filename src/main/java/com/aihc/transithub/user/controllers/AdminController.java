package com.aihc.transithub.user.controllers;

import com.aihc.transithub.user.dtos.AdminCreateDto;
import com.aihc.transithub.user.dtos.AdminResponseDto;
import com.aihc.transithub.user.dtos.AdminUpdateDto;
import com.aihc.transithub.user.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing admin operations in the system.
 *
 * @author Alvaro Huanca
 */
@RestController
@RequestMapping("/api/admins")
public class AdminController {

    @Autowired
    private AdminService adminService;

    /**
     * POST: Create a new admin
     * Endpoint: POST /api/admins
     */
    @PostMapping
    public ResponseEntity<AdminResponseDto> createAdmin(@RequestBody AdminCreateDto adminCreateDto) {
        try {
            AdminResponseDto adminResponseDto = adminService.createAdmin(adminCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(adminResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET: Get an admin by ID
     * Endpoint: GET /api/admins/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<AdminResponseDto> getAdminById(@PathVariable UUID id) {
        try {
            AdminResponseDto adminResponseDto = adminService.getAdminById(id);
            return ResponseEntity.ok(adminResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get an admin by username
     * Endpoint: GET /api/admins/username/{username}
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<AdminResponseDto> getAdminByUsername(@PathVariable String username) {
        try {
            AdminResponseDto adminResponseDto = adminService.getAdminByUsername(username);
            return ResponseEntity.ok(adminResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get all admins
     * Endpoint: GET /api/admins
     */
    @GetMapping
    public ResponseEntity<List<AdminResponseDto>> getAllAdmins() {
        List<AdminResponseDto> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    /**
     * PUT: Update an admin
     * Endpoint: PUT /api/admins/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<AdminResponseDto> updateAdmin(@PathVariable UUID id, @RequestBody AdminUpdateDto adminUpdateDto) {
        try {
            AdminResponseDto adminResponseDto = adminService.updateAdmin(id, adminUpdateDto);
            return ResponseEntity.ok(adminResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * DELETE: Delete an admin by ID
     * Endpoint: DELETE /api/admins/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable UUID id) {
        try {
            adminService.deleteAdmin(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
