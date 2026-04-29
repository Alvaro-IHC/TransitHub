package com.aihc.transithub.user.services;

import com.aihc.transithub.user.dtos.TicketAgentCreateDto;
import com.aihc.transithub.user.dtos.TicketAgentResponseDto;
import com.aihc.transithub.user.dtos.TicketAgentUpdateDto;
import com.aihc.transithub.user.entities.TicketAgent;
import com.aihc.transithub.user.repositories.TicketAgentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing ticket agent operations in the system.
 *
 * @author Alvaro Huanca
 */
@Service
public class TicketAgentService extends UserService {

    @Autowired
    private TicketAgentRepository ticketAgentRepository;

    /**
     * Create a new ticket agent.
     */
    public TicketAgentResponseDto createTicketAgent(TicketAgentCreateDto ticketAgentCreateDto) {
        validateTicketAgentCreate(ticketAgentCreateDto);
        
        TicketAgent ticketAgent = new TicketAgent();
        ticketAgent.setUsername(ticketAgentCreateDto.getUsername());
        ticketAgent.setPassword(ticketAgentCreateDto.getPassword());
        ticketAgent.setName(ticketAgentCreateDto.getName());
        ticketAgent.setLastName(ticketAgentCreateDto.getLastName());
        ticketAgent.setEmail(ticketAgentCreateDto.getEmail());
        ticketAgent.setPhone(ticketAgentCreateDto.getPhone());
        ticketAgent.setPhotoUrl(ticketAgentCreateDto.getPhotoUrl());
        ticketAgent.setTerminalName(ticketAgentCreateDto.getTerminalName());
        ticketAgent.setShiftStart(LocalTime.of(8, 0));
        ticketAgent.setShiftEnd(LocalTime.of(18, 0));
        ticketAgent.setPositionStartDate(ticketAgentCreateDto.getPositionStartDate());
        
        TicketAgent savedTicketAgent = ticketAgentRepository.save(ticketAgent);
        return mapToResponseDto(savedTicketAgent);
    }

    /**
     * Get a ticket agent by ID.
     */
    public TicketAgentResponseDto getTicketAgentById(UUID id) {
        TicketAgent ticketAgent = ticketAgentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket agent not found, ID: " + id));
        return mapToResponseDto(ticketAgent);
    }

    /**
     * Get a ticket agent by username.
     */
    public TicketAgentResponseDto getTicketAgentByUsername(String username) {
        TicketAgent ticketAgent = ticketAgentRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Ticket agent not found, username: " + username));
        return mapToResponseDto(ticketAgent);
    }

    /**
     * Get all ticket agents
     */
    public List<TicketAgentResponseDto> getAllTicketAgents() {
        return ticketAgentRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Update a ticket agent
     */
    public TicketAgentResponseDto updateTicketAgent(UUID id, TicketAgentUpdateDto ticketAgentUpdateDto) {
        TicketAgent ticketAgent = ticketAgentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket agent not found, ID: " + id));
        
        if (ticketAgentUpdateDto.getName() != null) {
            ticketAgent.setName(ticketAgentUpdateDto.getName());
        }
        if (ticketAgentUpdateDto.getLastName() != null) {
            ticketAgent.setLastName(ticketAgentUpdateDto.getLastName());
        }
        if (ticketAgentUpdateDto.getEmail() != null) {
            ticketAgent.setEmail(ticketAgentUpdateDto.getEmail());
        }
        if (ticketAgentUpdateDto.getPhone() != null) {
            ticketAgent.setPhone(ticketAgentUpdateDto.getPhone());
        }
        if (ticketAgentUpdateDto.getPhotoUrl() != null) {
            ticketAgent.setPhotoUrl(ticketAgentUpdateDto.getPhotoUrl());
        }
        if (ticketAgentUpdateDto.getCounterNumber() != null) {
            ticketAgent.setCounterNumber(ticketAgentUpdateDto.getCounterNumber());
        }
        if (ticketAgentUpdateDto.getTerminalName() != null) {
            ticketAgent.setTerminalName(ticketAgentUpdateDto.getTerminalName());
        }
        if (ticketAgentUpdateDto.getShiftStart() != null) {
            ticketAgent.setShiftStart(ticketAgentUpdateDto.getShiftStart());
        }
        if (ticketAgentUpdateDto.getShiftEnd() != null) {
            ticketAgent.setShiftEnd(ticketAgentUpdateDto.getShiftEnd());
        }
        if (ticketAgentUpdateDto.getPositionStartDate() != null) {
            ticketAgent.setPositionStartDate(ticketAgentUpdateDto.getPositionStartDate());
        }
        
        TicketAgent updatedTicketAgent = ticketAgentRepository.save(ticketAgent);
        return mapToResponseDto(updatedTicketAgent);
    }

    /**
     * Delete a ticket agent.
     */
    public void deleteTicketAgent(UUID id) {
        if (!ticketAgentRepository.existsById(id)) {
            throw new IllegalArgumentException("Ticket agent not found, ID: " + id);
        }
        ticketAgentRepository.deleteById(id);
    }

    /**
     * Validates ticket agent data
     */
    private void validateTicketAgentCreate(TicketAgentCreateDto ticketAgentCreateDto) {
        validateUserCreate(ticketAgentCreateDto);
    }

    /**
     * Maps TicketAgent entity to TicketAgentResponseDto
     */
    private TicketAgentResponseDto mapToResponseDto(TicketAgent ticketAgent) {
        return TicketAgentResponseDto.builder()
                .id(ticketAgent.getId())
                .username(ticketAgent.getUsername())
                .name(ticketAgent.getName())
                .lastName(ticketAgent.getLastName())
                .email(ticketAgent.getEmail())
                .phone(ticketAgent.getPhone())
                .photoUrl(ticketAgent.getPhotoUrl())
                .counterNumber(ticketAgent.getCounterNumber())
                .terminalName(ticketAgent.getTerminalName())
                .shiftStart(ticketAgent.getShiftStart())
                .shiftEnd(ticketAgent.getShiftEnd())
                .positionStartDate(ticketAgent.getPositionStartDate())
                .build();
    }
}
