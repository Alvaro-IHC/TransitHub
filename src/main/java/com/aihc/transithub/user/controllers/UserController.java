package com.aihc.transithub.user.controllers;

import com.aihc.transithub.user.dtos.UserCreateDto;
import com.aihc.transithub.user.dtos.UserResponseDto;
import com.aihc.transithub.user.dtos.UserUpdateDto;
import com.aihc.transithub.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing user operations in the system.
 *
 * @author Alvaro Huanca
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * POST: Create a new user
     * Endpoint: POST /api/users
     */
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserCreateDto userCreateDto) {
        try {
            UserResponseDto userResponseDto = userService.createUser(userCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET: Get a user by ID
     * Endpoint: GET /api/users/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable UUID id) {
        try {
            UserResponseDto userResponseDto = userService.getUserById(id);
            return ResponseEntity.ok(userResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get a user by username
     * Endpoint: GET /api/users/username/{username}
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDto> getUserByUsername(@PathVariable String username) {
        try {
            UserResponseDto userResponseDto = userService.getUserByUsername(username);
            return ResponseEntity.ok(userResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get all users
     * Endpoint: GET /api/users
     */
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * PUT: Update a user
     * Endpoint: PUT /api/users/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable UUID id,
            @RequestBody UserUpdateDto userUpdateDto) {
        try {
            UserResponseDto userResponseDto = userService.updateUser(id, userUpdateDto);
            return ResponseEntity.ok(userResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE: Delete a user
     * Endpoint: DELETE /api/users/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
