package com.aihc.transithub.travel.controllers;

import com.aihc.transithub.travel.dtos.TicketCreateDto;
import com.aihc.transithub.travel.dtos.TicketResponseDto;
import com.aihc.transithub.travel.dtos.TicketUpdateDto;
import com.aihc.transithub.travel.services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

/**
 * REST controller for managing ticket operations in the system.
 *
 * @author Alvaro Huanca
 */
@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    /**
     * POST: Create a new ticket
     * Endpoint: POST /api/tickets
     */
    @PostMapping
    public ResponseEntity<TicketResponseDto> createTicket(@RequestBody TicketCreateDto ticketCreateDto) {
        try {
            TicketResponseDto ticketResponseDto = ticketService.createTicket(ticketCreateDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(ticketResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * GET: Get a ticket by ID
     * Endpoint: GET /api/tickets/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<TicketResponseDto> getTicketById(@PathVariable UUID id) {
        try {
            TicketResponseDto ticketResponseDto = ticketService.getTicketById(id);
            return ResponseEntity.ok(ticketResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * GET: Get all tickets or filter by trip or agent
     * Endpoint: GET /api/tickets or GET /api/tickets?tripId=xxx or GET /api/tickets?agentId=xxx
     */
    @GetMapping
    public ResponseEntity<List<TicketResponseDto>> getTickets(
            @RequestParam(required = false) UUID tripId,
            @RequestParam(required = false) UUID agentId) {
        try {
            List<TicketResponseDto> tickets;
            if (tripId != null) {
                tickets = ticketService.getTicketsByTripId(tripId);
            } else if (agentId != null) {
                tickets = ticketService.getTicketsByAgentId(agentId);
            } else {
                tickets = ticketService.getAllTickets();
            }
            return ResponseEntity.ok(tickets);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * PUT: Update a ticket
     * Endpoint: PUT /api/tickets/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<TicketResponseDto> updateTicket(
            @PathVariable UUID id,
            @RequestBody TicketUpdateDto ticketUpdateDto) {
        try {
            TicketResponseDto ticketResponseDto = ticketService.updateTicket(id, ticketUpdateDto);
            return ResponseEntity.ok(ticketResponseDto);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * DELETE: Delete a ticket
     * Endpoint: DELETE /api/tickets/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicket(@PathVariable UUID id) {
        try {
            ticketService.deleteTicket(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
