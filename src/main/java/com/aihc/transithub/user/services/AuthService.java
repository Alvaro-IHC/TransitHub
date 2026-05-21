package com.aihc.transithub.user.services;

import com.aihc.transithub.auth.dtos.ErrorResponseDto;
import com.aihc.transithub.auth.dtos.LoginRequestDto;
import com.aihc.transithub.auth.dtos.LoginResponseDto;
import com.aihc.transithub.config.security.JwtProvider;
import com.aihc.transithub.config.security.PasswordEncoder;
import com.aihc.transithub.user.dtos.UserWithRoleDto;
import com.aihc.transithub.user.entities.Admin;
import com.aihc.transithub.user.entities.Driver;
import com.aihc.transithub.user.entities.TicketAgent;
import com.aihc.transithub.user.entities.Treasurer;
import com.aihc.transithub.user.entities.User;
import com.aihc.transithub.user.enums.UserRole;
import com.aihc.transithub.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * Service class for handling authentication operations in the system.
 *
 * @author Alvaro Huanca
 */
@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Authenticate user with username/email and password.
     * Returns user data with role if credentials are valid.
     */
    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        // Find user by username or email
        User user = userRepository.findByUsername(loginRequestDto.getUsernameOrEmail())
                .orElseGet(() -> userRepository.findByEmail(loginRequestDto.getUsernameOrEmail())
                        .orElse(null));

        // Determine user role
        UserRole role = determineUserRole(user);

        String token = jwtProvider.generateToken(user.getId(), user.getUsername(), role.name());

        return new LoginResponseDto(
                token,
                "Authentication successful",
                UserWithRoleDto.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .name(user.getName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .phone(user.getPhone())
                    .photoUrl(user.getPhotoUrl())
                    .role(role)
                    .build()
        );
    }

    public ResponseEntity<?> validate(LoginRequestDto loginRequestDto) {
        if (loginRequestDto.getUsernameOrEmail() == null || loginRequestDto.getUsernameOrEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), "Username or email is required"));
        }

        if (loginRequestDto.getPassword() == null || loginRequestDto.getPassword().trim().isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), "Password is required"));
        }

        User user = userRepository.findByUsername(loginRequestDto.getUsernameOrEmail())
            .orElseGet(() -> userRepository.findByEmail(loginRequestDto.getUsernameOrEmail())
                    .orElse(null));

        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), "Invalid username, email or password"));
        }

        // Validate password with BCrypt
        if (!passwordEncoder.matches(loginRequestDto.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ErrorResponseDto(HttpStatus.UNAUTHORIZED.value(), "Invalid username, email or password"));
        }
        return null;
    }

    /**
     * Determine the role of a user based on their type.
     */
    private UserRole determineUserRole(User user) {
        if (user instanceof Admin) {
            return UserRole.ADMIN;
        } else if (user instanceof Driver) {
            return UserRole.DRIVER;
        } else if (user instanceof Treasurer) {
            return UserRole.TREASURER;
        } else if (user instanceof TicketAgent) {
            return UserRole.TICKET_AGENT;
        }
        throw new IllegalStateException("Unknown user role for user: " + user.getId());
    }
}

