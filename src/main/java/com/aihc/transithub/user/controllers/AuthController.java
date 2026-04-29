package com.aihc.transithub.user.controllers;

import com.aihc.transithub.user.dtos.LoginRequestDto;
import com.aihc.transithub.user.dtos.UserWithRoleDto;
import com.aihc.transithub.user.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling authentication operations in the system.
 *
 * @author Alvaro Huanca
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * POST: Login with username/email and password
     * Endpoint: POST /api/auth/login
     * Returns UserWithRoleDto if credentials are valid
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            // Validate input
            if (loginRequestDto.getUsernameOrEmail() == null || loginRequestDto.getUsernameOrEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Username or email is required");
            }

            if (loginRequestDto.getPassword() == null || loginRequestDto.getPassword().trim().isEmpty()) {
                return ResponseEntity.badRequest()
                        .body("Password is required");
            }

            // Attempt login
            UserWithRoleDto userWithRole = authService.login(loginRequestDto);
            return ResponseEntity.ok(userWithRole);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Invalid username, email or password");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred during login");
        }
    }
}

