package com.aihc.transithub.travel.services;

import com.aihc.transithub.travel.dtos.TicketCreateDto;
import com.aihc.transithub.travel.dtos.TicketResponseDto;
import com.aihc.transithub.travel.dtos.TicketUpdateDto;
import com.aihc.transithub.travel.entities.Ticket;
import com.aihc.transithub.travel.entities.Trip;
import com.aihc.transithub.travel.repositories.TicketRepository;
import com.aihc.transithub.travel.repositories.TripRepository;
import com.aihc.transithub.user.entities.TicketAgent;
import com.aihc.transithub.user.repositories.TicketAgentRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing ticket operations in the system.
 *
 * @author Alvaro Huanca
 */
@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TicketAgentRepository ticketAgentRepository;

    /**
     * Create a new ticket
     */
    public TicketResponseDto createTicket(TicketCreateDto ticketCreateDto) {
        validateTicketCreate(ticketCreateDto);

        Ticket ticket = new Ticket();
        ticket.setPassengerName(ticketCreateDto.getPassengerName());
        ticket.setSeatNumber(ticketCreateDto.getSeatNumber());
        ticket.setCost(ticketCreateDto.getCost());
        ticket.setDestination(ticketCreateDto.getDestination());
        ticket.setDate(ticketCreateDto.getDate());

        Trip trip = tripRepository.findById(ticketCreateDto.getTripId())
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with ID: " + ticketCreateDto.getTripId()));
        ticket.setTrip(trip);

        TicketAgent agent = ticketAgentRepository.findById(ticketCreateDto.getAgentId())
                .orElseThrow(() -> new IllegalArgumentException("Ticket Agent not found with ID: " + ticketCreateDto.getAgentId()));
        ticket.setSoldBy(agent);

        Ticket savedTicket = ticketRepository.save(ticket);
        return mapToResponseDto(savedTicket);
    }

    /**
     * Get a ticket by ID
     */
    public TicketResponseDto getTicketById(UUID id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + id));
        return mapToResponseDto(ticket);
    }

    /**
     * Get all tickets
     */
    public List<TicketResponseDto> getAllTickets() {
        return ticketRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Get tickets by trip ID
     */
    public List<TicketResponseDto> getTicketsByTripId(UUID tripId) {
        return ticketRepository.findByTripId(tripId)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Get tickets sold by a specific ticket agent
     */
    public List<TicketResponseDto> getTicketsByAgentId(UUID agentId) {
        return ticketRepository.findBySoldBy(agentId)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Update a ticket
     */
    public TicketResponseDto updateTicket(UUID id, TicketUpdateDto ticketUpdateDto) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ticket not found with ID: " + id));

        if (ticketUpdateDto.getPassengerName() != null) {
            ticket.setPassengerName(ticketUpdateDto.getPassengerName());
        }
        if (ticketUpdateDto.getSeatNumber() != null) {
            ticket.setSeatNumber(ticketUpdateDto.getSeatNumber());
        }
        if (ticketUpdateDto.getCost() != null) {
            ticket.setCost(ticketUpdateDto.getCost());
        }
        if (ticketUpdateDto.getDestination() != null) {
            ticket.setDestination(ticketUpdateDto.getDestination());
        }
        if (ticketUpdateDto.getDate() != null) {
            ticket.setDate(ticketUpdateDto.getDate());
        }
        if (ticketUpdateDto.getTripId() != null) {
            Trip trip = tripRepository.findById(ticketUpdateDto.getTripId())
                    .orElseThrow(() -> new IllegalArgumentException("Trip not found with ID: " + ticketUpdateDto.getTripId()));
            ticket.setTrip(trip);
        }
        if (ticketUpdateDto.getAgentId() != null) {
            TicketAgent agent = ticketAgentRepository.findById(ticketUpdateDto.getAgentId())
                    .orElseThrow(() -> new IllegalArgumentException("Ticket Agent not found with ID: " + ticketUpdateDto.getAgentId()));
            ticket.setSoldBy(agent);
        }

        Ticket updatedTicket = ticketRepository.save(ticket);
        return mapToResponseDto(updatedTicket);
    }

    /**
     * Delete a ticket by ID
     */
    public void deleteTicket(UUID id) {
        if (!ticketRepository.existsById(id)) {
            throw new IllegalArgumentException("Ticket not found with ID: " + id);
        }
        ticketRepository.deleteById(id);
    }

    /**
     * Validate data for creating ticket
     */
    private void validateTicketCreate(TicketCreateDto ticketCreateDto) {
        if (StringUtils.isBlank(ticketCreateDto.getPassengerName())) {
            throw new IllegalArgumentException("Passenger name is required");
        }

        if (ticketCreateDto.getSeatNumber() == null) {
            throw new IllegalArgumentException("Seat number is required");
        }

        if (ticketCreateDto.getCost() == null) {
            throw new IllegalArgumentException("Cost is required");
        }

        if (ticketCreateDto.getTripId() == null) {
            throw new IllegalArgumentException("Trip ID is required");
        }

        if (ticketCreateDto.getAgentId() == null) {
            throw new IllegalArgumentException("Agent ID is required");
        }
    }

    /**
     * Map Ticket entity to TicketResponseDto
     */
    private TicketResponseDto mapToResponseDto(Ticket ticket) {
        return TicketResponseDto.builder()
                .id(ticket.getId())
                .passengerName(ticket.getPassengerName())
                .seatNumber(ticket.getSeatNumber())
                .cost(ticket.getCost())
                .destination(ticket.getDestination())
                .date(ticket.getDate())
                .tripId(ticket.getTrip() != null ? ticket.getTrip().getId() : null)
                .agentId(ticket.getSoldBy() != null ? ticket.getSoldBy().getId() : null)
                .build();
    }
}
