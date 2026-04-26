package com.aihc.transithub.user.services;

import com.aihc.transithub.user.dtos.TreasurerCreateDto;
import com.aihc.transithub.user.dtos.TreasurerResponseDto;
import com.aihc.transithub.user.dtos.TreasurerUpdateDto;
import com.aihc.transithub.user.entities.Treasurer;
import com.aihc.transithub.user.repositories.TreasurerRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing treasurer operations in the system.
 *
 * @author Alvaro Huanca
 */
@Service
public class TreasurerService extends UserService {

    @Autowired
    private TreasurerRepository treasurerRepository;

    /**
     * Create a new treasurer
     */
    public TreasurerResponseDto createTreasurer(TreasurerCreateDto treasurerCreateDto) {
        validateTreasurerCreate(treasurerCreateDto);

        Treasurer treasurer = new Treasurer();
        treasurer.setUsername(treasurerCreateDto.getUsername());
        treasurer.setPassword(treasurerCreateDto.getPassword());
        treasurer.setName(treasurerCreateDto.getName());
        treasurer.setLastName(treasurerCreateDto.getLastName());
        treasurer.setEmail(treasurerCreateDto.getEmail());
        treasurer.setPhone(treasurerCreateDto.getPhone());
        treasurer.setOfficeNumber(treasurerCreateDto.getOfficeNumber());
        treasurer.setPositionStartDate(treasurerCreateDto.getPositionStartDate());
        treasurer.setBankAccount(treasurerCreateDto.getBankAccount());

        Treasurer savedTreasurer = treasurerRepository.save(treasurer);
        return mapToResponseDto(savedTreasurer);
    }

    /**
     * Get a treasurer by id
     */
    public TreasurerResponseDto getTreasurerById(UUID id) {
        Treasurer treasurer = treasurerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Treasurer not found, ID: " + id));
        return mapToResponseDto(treasurer);
    }

    /**
     * Get a treasurer by username
     */
    public TreasurerResponseDto getTreasurerByUsername(String username) {
        Treasurer treasurer = treasurerRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Treasurer not found, username: " + username));
        return mapToResponseDto(treasurer);
    }

    /**
     * Get all treasurers
     */
    public List<TreasurerResponseDto> getAllTreasurers() {
        return treasurerRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Update a treasurer
     */
    public TreasurerResponseDto updateTreasurer(UUID id, TreasurerUpdateDto treasurerUpdateDto) {
        Treasurer treasurer = treasurerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Treasurer not found, ID: " + id));

        if (treasurerUpdateDto.getName() != null) {
            treasurer.setName(treasurerUpdateDto.getName());
        }
        if (treasurerUpdateDto.getLastName() != null) {
            treasurer.setLastName(treasurerUpdateDto.getLastName());
        }
        if (treasurerUpdateDto.getEmail() != null) {
            treasurer.setEmail(treasurerUpdateDto.getEmail());
        }
        if (treasurerUpdateDto.getPhone() != null) {
            treasurer.setPhone(treasurerUpdateDto.getPhone());
        }
        if (treasurerUpdateDto.getOfficeNumber() != null) {
            treasurer.setOfficeNumber(treasurerUpdateDto.getOfficeNumber());
        }
        if (treasurerUpdateDto.getPositionStartDate() != null) {
            treasurer.setPositionStartDate(treasurerUpdateDto.getPositionStartDate());
        }
        if (treasurerUpdateDto.getBankAccount() != null) {
            treasurer.setBankAccount(treasurerUpdateDto.getBankAccount());
        }

        Treasurer updatedTreasurer = treasurerRepository.save(treasurer);
        return mapToResponseDto(updatedTreasurer);
    }

    /**
     * Delete a treasurer by id
     */
    public void deleteTreasurer(UUID id) {
        if (!treasurerRepository.existsById(id)) {
            throw new IllegalArgumentException("Treasurer not found, ID: " + id);
        }
        treasurerRepository.deleteById(id);
    }

    /**
     * Validates treasurer data
     */
    private void validateTreasurerCreate(TreasurerCreateDto treasurerCreateDto) {
        validateUserCreate(treasurerCreateDto);

//        if (StringUtils.isBlank(treasurerCreateDto.getOfficeNumber())) {
//            throw new IllegalArgumentException("Office number is required");
//        }
    }

    /**
     * Maps Treasurer entity to TreasurerResponseDto
     */
    private TreasurerResponseDto mapToResponseDto(Treasurer treasurer) {
        return TreasurerResponseDto.builder()
                .id(treasurer.getId())
                .username(treasurer.getUsername())
                .name(treasurer.getName())
                .lastName(treasurer.getLastName())
                .email(treasurer.getEmail())
                .phone(treasurer.getPhone())
                .officeNumber(treasurer.getOfficeNumber())
                .positionStartDate(treasurer.getPositionStartDate())
                .bankAccount(treasurer.getBankAccount())
                .build();
    }
}
