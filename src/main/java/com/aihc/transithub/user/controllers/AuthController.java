package com.aihc.transithub.user.controllers;

import com.aihc.transithub.auth.dtos.LoginRequestDto;
import com.aihc.transithub.user.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for handling authentication operations in the system.
 * Provides login endpoint with JWT token generation.
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
     * Returns JWT token with user information if credentials are valid
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto) {
        ResponseEntity<?> error = authService.validate(loginRequestDto);
        if (error == null) {
            return ResponseEntity.ok(authService.login(loginRequestDto));
        }
        return error;
    }
}
