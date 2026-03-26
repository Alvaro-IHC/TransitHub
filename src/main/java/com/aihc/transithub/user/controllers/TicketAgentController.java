package com.aihc.transithub.user.controllers;

import com.aihc.transithub.user.dtos.TicketAgentCreateDto;
import com.aihc.transithub.user.dtos.TicketAgentResponseDto;
import com.aihc.transithub.user.dtos.TicketAgentUpdateDto;
import com.aihc.transithub.user.services.TicketAgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing ticket agent operations in the system.
 *
 * @author Alvaro Huanca
 */
@RestController
@RequestMapping("/api/ticket-agents")
public class TicketAgentController {

    @Autowired
    private TicketAgentService ticketAgentService;

    /**
     * POST: Create a new ticket agent
     * Endpoint: POST /api/ticket-agents
     */
    @PostMapping
    public ResponseEntity<TicketAgentResponseDto> createTicketAgent(@RequestBody TicketAgentCreateDto ticketAgentCreateDto) {
        try {
            TicketAgentResponseDto ticketAgentResponseDto = ticketAgentService.createTicketAgent(ticketAgentCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ticketAgentResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET: Get a ticket agent by ID
     * Endpoint: GET /api/ticket-agents/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TicketAgentResponseDto> getTicketAgentById(@PathVariable UUID id) {
        try {
            TicketAgentResponseDto ticketAgentResponseDto = ticketAgentService.getTicketAgentById(id);
            return ResponseEntity.ok(ticketAgentResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get a ticket agent by username
     * Endpoint: GET /api/ticket-agents/username/{username}
     */
    @GetMapping("/username/{username}")
    public ResponseEntity<TicketAgentResponseDto> getTicketAgentByUsername(@PathVariable String username) {
        try {
            TicketAgentResponseDto ticketAgentResponseDto = ticketAgentService.getTicketAgentByUsername(username);
            return ResponseEntity.ok(ticketAgentResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get all ticket agents
     * Endpoint: GET /api/ticket-agents
     */
    @GetMapping
    public ResponseEntity<List<TicketAgentResponseDto>> getAllTicketAgents() {
        List<TicketAgentResponseDto> ticketAgents = ticketAgentService.getAllTicketAgents();
        return ResponseEntity.ok(ticketAgents);
    }

    /**
     * PUT: Update a ticket agent
     * Endpoint: PUT /api/ticket-agents/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TicketAgentResponseDto> updateTicketAgent(
            @PathVariable UUID id,
            @RequestBody TicketAgentUpdateDto ticketAgentUpdateDto) {
        try {
            TicketAgentResponseDto ticketAgentResponseDto = ticketAgentService.updateTicketAgent(id, ticketAgentUpdateDto);
            return ResponseEntity.ok(ticketAgentResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE: Delete a ticket agent
     * Endpoint: DELETE /api/ticket-agents/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketAgent(@PathVariable UUID id) {
        try {
            ticketAgentService.deleteTicketAgent(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
