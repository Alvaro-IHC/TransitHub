package com.aihc.transithub.user.services;

import com.aihc.transithub.user.dtos.LoginRequestDto;
import com.aihc.transithub.user.dtos.UserWithRoleDto;
import com.aihc.transithub.user.entities.Admin;
import com.aihc.transithub.user.entities.Driver;
import com.aihc.transithub.user.entities.TicketAgent;
import com.aihc.transithub.user.entities.Treasurer;
import com.aihc.transithub.user.entities.User;
import com.aihc.transithub.user.enums.UserRole;
import com.aihc.transithub.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    private AdminService adminService;

    @Autowired
    private DriverService driverService;

    @Autowired
    private TreasurerService treasurerService;

    @Autowired
    private TicketAgentService ticketAgentService;

    /**
     * Authenticate user with username/email and password.
     * Returns user data with role if credentials are valid.
     */
    public UserWithRoleDto login(LoginRequestDto loginRequestDto) {
        // Find user by username or email
        User user = userRepository.findByUsername(loginRequestDto.getUsernameOrEmail())
                .orElseGet(() -> userRepository.findByEmail(loginRequestDto.getUsernameOrEmail())
                        .orElse(null));

        if (user == null) {
            throw new IllegalArgumentException("Invalid username, email or password");
        }

        // Validate password
        if (!user.getPassword().equals(loginRequestDto.getPassword())) {
            throw new IllegalArgumentException("Invalid username, email or password");
        }

        // Determine user role
        UserRole role = determineUserRole(user);

        // Map to UserWithRoleDto
        return UserWithRoleDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .photoUrl(user.getPhotoUrl())
                .role(role)
                .build();
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

