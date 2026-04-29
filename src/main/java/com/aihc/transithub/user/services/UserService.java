package com.aihc.transithub.user.services;

import com.aihc.transithub.user.dtos.UserCreateDto;
import com.aihc.transithub.user.dtos.UserResponseDto;
import com.aihc.transithub.user.dtos.UserUpdateDto;
import com.aihc.transithub.user.dtos.UserWithRoleDto;
import com.aihc.transithub.user.entities.User;
import com.aihc.transithub.user.enums.UserRole;
import com.aihc.transithub.user.repositories.AdminRepository;
import com.aihc.transithub.user.repositories.DriverRepository;
import com.aihc.transithub.user.repositories.TreasurerRepository;
import com.aihc.transithub.user.repositories.UserRepository;
import com.aihc.transithub.user.repositories.TicketAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing user operations in the TransitHub system.
 *
 * @author Alvaro Huanca
 */
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private TreasurerRepository treasurerRepository;

    @Autowired
    private TicketAgentRepository ticketAgentRepository;

    /**
     * Create a new user
     */
    public UserResponseDto createUser(UserCreateDto userCreateDto) {
        validateUserCreate(userCreateDto);
        
        User user = new User();
        user.setUsername(userCreateDto.getUsername());
        user.setPassword(userCreateDto.getPassword());
        user.setName(userCreateDto.getName());
        user.setLastName(userCreateDto.getLastName());
        user.setEmail(userCreateDto.getEmail());
        user.setPhone(userCreateDto.getPhone());
        user.setPhotoUrl(userCreateDto.getPhotoUrl());

        User savedUser = userRepository.save(user);
        return mapToResponseDto(savedUser);
    }

    /**
     * Get a user by ID
     */
    public UserResponseDto getUserById(UUID id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        return mapToResponseDto(user);
    }

    /**
     * Get a user by username
     */
    public UserResponseDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        return mapToResponseDto(user);
    }

    /**
     * Get all users
     */
    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Update a user
     */
    public UserResponseDto updateUser(UUID id, UserUpdateDto userUpdateDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + id));
        
        if (userUpdateDto.getName() != null) {
            user.setName(userUpdateDto.getName());
        }
        if (userUpdateDto.getLastName() != null) {
            user.setLastName(userUpdateDto.getLastName());
        }
        if (userUpdateDto.getEmail() != null) {
            user.setEmail(userUpdateDto.getEmail());
        }
        if (userUpdateDto.getPhone() != null) {
            user.setPhone(userUpdateDto.getPhone());
        }
        if (userUpdateDto.getPhotoUrl() != null) {
            user.setPhotoUrl(userUpdateDto.getPhotoUrl());
        }

        User updatedUser = userRepository.save(user);
        return mapToResponseDto(updatedUser);
    }

    /**
     * Delete a user by ID
     */
    public void deleteUser(UUID id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    /**
     * Validate data for creating user
     */
    protected void validateUserCreate(UserCreateDto userCreateDto) {
        if (userCreateDto.getUsername() == null || userCreateDto.getUsername().trim().isEmpty()) {
            throw new IllegalArgumentException("Username is required");
        }
        
        if (userRepository.existsByUsername(userCreateDto.getUsername())) {
            throw new IllegalArgumentException("Username already exists: " + userCreateDto.getUsername());
        }
        
        if (userCreateDto.getPassword() == null || userCreateDto.getPassword().trim().isEmpty()) {
            throw new IllegalArgumentException("Password is required");
        }
        
        if (userCreateDto.getPassword().length() < 6) {
            throw new IllegalArgumentException("Password must be at least 6 characters");
        }
    }

    /**
     * Map User entity to UserResponseDto
     */
    private UserResponseDto mapToResponseDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .photoUrl(user.getPhotoUrl())
                .build();
    }

    /**
     * Get users by specific roles
     * If roles is null or empty, returns all users
     */
    public List<UserWithRoleDto> getUsersByRoles(List<String> roles) {
        List<UserRole> userRoles = roles == null || roles.isEmpty() 
                ? List.of(UserRole.values()) 
                : roles.stream().map(UserRole::valueOf).collect(Collectors.toList());

        List<UserWithRoleDto> result = new java.util.ArrayList<>();

        for (UserRole role : userRoles) {
            switch (role) {
                case ADMIN:
                    result.addAll(adminRepository.findAll()
                            .stream()
                            .map(admin -> mapToUserWithRoleDto(admin, UserRole.ADMIN))
                            .collect(Collectors.toList()));
                    break;
                case TICKET_AGENT:
                    result.addAll(ticketAgentRepository.findAll()
                            .stream()
                            .map(agent -> mapToUserWithRoleDto(agent, UserRole.TICKET_AGENT))
                            .collect(Collectors.toList()));
                    break;
                case DRIVER:
                    result.addAll(driverRepository.findAll()
                            .stream()
                            .map(driver -> mapToUserWithRoleDto(driver, UserRole.DRIVER))
                            .collect(Collectors.toList()));
                    break;
                case TREASURER:
                    result.addAll(treasurerRepository.findAll()
                            .stream()
                            .map(treasurer -> mapToUserWithRoleDto(treasurer, UserRole.TREASURER))
                            .collect(Collectors.toList()));
                    break;
            }
        }

        return result;
    }

    /**
     * Map User entity to UserWithRoleDto with role information
     */
    protected UserWithRoleDto mapToUserWithRoleDto(User user, UserRole role) {
        return UserWithRoleDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(role)
                .photoUrl(user.getPhotoUrl())
                .build();
    }
}
