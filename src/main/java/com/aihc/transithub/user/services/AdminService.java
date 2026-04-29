package com.aihc.transithub.user.services;

import com.aihc.transithub.user.dtos.AdminCreateDto;
import com.aihc.transithub.user.dtos.AdminResponseDto;
import com.aihc.transithub.user.dtos.AdminUpdateDto;
import com.aihc.transithub.user.entities.Admin;
import com.aihc.transithub.user.repositories.AdminRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing admin operations in the system.
 *
 * @author Alvaro Huanca
 */
@Service
public class AdminService extends UserService {

    @Autowired
    private AdminRepository adminRepository;

    /**
     * Create a new admin
     */
    public AdminResponseDto createAdmin(AdminCreateDto adminCreateDto) {
        validateAdminCreate(adminCreateDto);

        Admin admin = new Admin();
        admin.setUsername(adminCreateDto.getUsername());
        admin.setPassword(adminCreateDto.getPassword());
        admin.setName(adminCreateDto.getName());
        admin.setLastName(adminCreateDto.getLastName());
        admin.setEmail(adminCreateDto.getEmail());
        admin.setPhone(adminCreateDto.getPhone());
        admin.setPhotoUrl(adminCreateDto.getPhotoUrl());
        admin.setPosition(adminCreateDto.getPosition());
        admin.setPositionStartDate(adminCreateDto.getPositionStartDate());
        admin.setAccessLevel(adminCreateDto.getAccessLevel());

        Admin savedAdmin = adminRepository.save(admin);
        return mapToResponseDto(savedAdmin);
    }

    /**
     * Get an admin by id
     */
    public AdminResponseDto getAdminById(UUID id) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found, ID: " + id));
        return mapToResponseDto(admin);
    }

    /**
     * Get an admin by username
     */
    public AdminResponseDto getAdminByUsername(String username) {
        Admin admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found, username: " + username));
        return mapToResponseDto(admin);
    }

    /**
     * Get all admins
     */
    public List<AdminResponseDto> getAllAdmins() {
        return adminRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Update an admin
     */
    public AdminResponseDto updateAdmin(UUID id, AdminUpdateDto adminUpdateDto) {
        Admin admin = adminRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Admin not found, ID: " + id));

        if (adminUpdateDto.getName() != null) {
            admin.setName(adminUpdateDto.getName());
        }
        if (adminUpdateDto.getLastName() != null) {
            admin.setLastName(adminUpdateDto.getLastName());
        }
        if (adminUpdateDto.getEmail() != null) {
            admin.setEmail(adminUpdateDto.getEmail());
        }
        if (adminUpdateDto.getPhone() != null) {
            admin.setPhone(adminUpdateDto.getPhone());
        }
        if (adminUpdateDto.getPhotoUrl() != null) {
            admin.setPhotoUrl(adminUpdateDto.getPhotoUrl());
        }
        if (adminUpdateDto.getPosition() != null) {
            admin.setPosition(adminUpdateDto.getPosition());
        }
        if (adminUpdateDto.getPositionStartDate() != null) {
            admin.setPositionStartDate(adminUpdateDto.getPositionStartDate());
        }
        if (adminUpdateDto.getAccessLevel() != null) {
            admin.setAccessLevel(adminUpdateDto.getAccessLevel());
        }

        Admin updatedAdmin = adminRepository.save(admin);
        return mapToResponseDto(updatedAdmin);
    }

    /**
     * Delete an admin by id
     */
    public void deleteAdmin(UUID id) {
        if (!adminRepository.existsById(id)) {
            throw new IllegalArgumentException("Admin not found, ID: " + id);
        }
        adminRepository.deleteById(id);
    }

    /**
     * Validates admin data
     */
    private void validateAdminCreate(AdminCreateDto adminCreateDto) {
        validateUserCreate(adminCreateDto);

//        if (StringUtils.isBlank(adminCreateDto.getPosition())) {
//            throw new IllegalArgumentException("Position is required");
//        }
    }

    /**
     * Maps Admin entity to AdminResponseDto
     */
    private AdminResponseDto mapToResponseDto(Admin admin) {
        return AdminResponseDto.builder()
                .id(admin.getId())
                .username(admin.getUsername())
                .name(admin.getName())
                .lastName(admin.getLastName())
                .email(admin.getEmail())
                .phone(admin.getPhone())
                .photoUrl(admin.getPhotoUrl())
                .position(admin.getPosition())
                .positionStartDate(admin.getPositionStartDate())
                .accessLevel(admin.getAccessLevel())
                .build();
    }
}
