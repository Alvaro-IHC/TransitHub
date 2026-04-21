package com.aihc.transithub.travel.services;

import com.aihc.transithub.travel.dtos.ParcelCreateDto;
import com.aihc.transithub.travel.dtos.ParcelResponseDto;
import com.aihc.transithub.travel.dtos.ParcelUpdateDto;
import com.aihc.transithub.travel.entities.Parcel;
import com.aihc.transithub.travel.entities.Trip;
import com.aihc.transithub.travel.repositories.ParcelRepository;
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
 * Service class for managing parcel operations in the system.
 *
 * @author Alvaro Huanca
 */
@Service
public class ParcelService {

    @Autowired
    private ParcelRepository parcelRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TicketAgentRepository ticketAgentRepository;

    /**
     * Create a new parcel
     */
    public ParcelResponseDto createParcel(ParcelCreateDto parcelCreateDto) {
        validateParcelCreate(parcelCreateDto);

        Parcel parcel = new Parcel();
        parcel.setSenderName(parcelCreateDto.getSenderName());
        parcel.setSenderPhone(parcelCreateDto.getSenderPhone());
        parcel.setReceiverName(parcelCreateDto.getReceiverName());
        parcel.setReceiverPhone(parcelCreateDto.getReceiverPhone());
        parcel.setDestination(parcelCreateDto.getDestination());
        parcel.setDescription(parcelCreateDto.getDescription());
        parcel.setCost(parcelCreateDto.getCost());
        parcel.setDate(parcelCreateDto.getDate());

        Trip trip = tripRepository.findById(parcelCreateDto.getTripId())
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with ID: " + parcelCreateDto.getTripId()));
        parcel.setTrip(trip);

        TicketAgent agent = ticketAgentRepository.findById(parcelCreateDto.getAgentId())
                .orElseThrow(() -> new IllegalArgumentException("Ticket Agent not found with ID: " + parcelCreateDto.getAgentId()));
        parcel.setRegisterBy(agent);

        Parcel savedParcel = parcelRepository.save(parcel);
        return mapToResponseDto(savedParcel);
    }

    /**
     * Get a parcel by ID
     */
    public ParcelResponseDto getParcelById(UUID id) {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Parcel not found with ID: " + id));
        return mapToResponseDto(parcel);
    }

    /**
     * Get all parcels or filter by trip or agent
     */
    public List<ParcelResponseDto> getParcels(UUID tripId, UUID agentId) {
        List<Parcel> parcels;

        if (tripId != null) {
            parcels = parcelRepository.findByTripId(tripId);
        } else if (agentId != null) {
            parcels = parcelRepository.findByRegisterById(agentId);
        } else {
            parcels = parcelRepository.findAll();
        }

        return parcels.stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Update a parcel
     */
    public ParcelResponseDto updateParcel(UUID id, ParcelUpdateDto parcelUpdateDto) {
        Parcel parcel = parcelRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Parcel not found with ID: " + id));

        if (parcelUpdateDto.getSenderName() != null) {
            parcel.setSenderName(parcelUpdateDto.getSenderName());
        }
        if (parcelUpdateDto.getSenderPhone() != null) {
            parcel.setSenderPhone(parcelUpdateDto.getSenderPhone());
        }
        if (parcelUpdateDto.getReceiverName() != null) {
            parcel.setReceiverName(parcelUpdateDto.getReceiverName());
        }
        if (parcelUpdateDto.getReceiverPhone() != null) {
            parcel.setReceiverPhone(parcelUpdateDto.getReceiverPhone());
        }
        if (parcelUpdateDto.getDestination() != null) {
            parcel.setDestination(parcelUpdateDto.getDestination());
        }
        if (parcelUpdateDto.getDescription() != null) {
            parcel.setDescription(parcelUpdateDto.getDescription());
        }
        if (parcelUpdateDto.getCost() != null) {
            parcel.setCost(parcelUpdateDto.getCost());
        }
        if (parcelUpdateDto.getDate() != null) {
            parcel.setDate(parcelUpdateDto.getDate());
        }
        if (parcelUpdateDto.getTripId() != null) {
            Trip trip = tripRepository.findById(parcelUpdateDto.getTripId())
                    .orElseThrow(() -> new IllegalArgumentException("Trip not found with ID: " + parcelUpdateDto.getTripId()));
            parcel.setTrip(trip);
        }
        if (parcelUpdateDto.getAgentId() != null) {
            TicketAgent agent = ticketAgentRepository.findById(parcelUpdateDto.getAgentId())
                    .orElseThrow(() -> new IllegalArgumentException("Ticket Agent not found with ID: " + parcelUpdateDto.getAgentId()));
            parcel.setRegisterBy(agent);
        }

        Parcel updatedParcel = parcelRepository.save(parcel);
        return mapToResponseDto(updatedParcel);
    }

    /**
     * Delete a parcel by ID
     */
    public void deleteParcel(UUID id) {
        if (!parcelRepository.existsById(id)) {
            throw new IllegalArgumentException("Parcel not found with ID: " + id);
        }
        parcelRepository.deleteById(id);
    }

    /**
     * Validate data for creating parcel
     */
    private void validateParcelCreate(ParcelCreateDto parcelCreateDto) {
        if (StringUtils.isBlank(parcelCreateDto.getSenderName())) {
            throw new IllegalArgumentException("Sender name is required");
        }

        if (StringUtils.isBlank(parcelCreateDto.getSenderPhone())) {
            throw new IllegalArgumentException("Sender phone is required");
        }

        if (StringUtils.isBlank(parcelCreateDto.getReceiverName())) {
            throw new IllegalArgumentException("Receiver name is required");
        }

        if (StringUtils.isBlank(parcelCreateDto.getReceiverPhone())) {
            throw new IllegalArgumentException("Receiver phone is required");
        }

        if (parcelCreateDto.getCost() == null) {
            throw new IllegalArgumentException("Cost is required");
        }

        if (parcelCreateDto.getTripId() == null) {
            throw new IllegalArgumentException("Trip ID is required");
        }

        if (parcelCreateDto.getAgentId() == null) {
            throw new IllegalArgumentException("Agent ID is required");
        }
    }

    /**
     * Map Parcel entity to ParcelResponseDto
     */
    private ParcelResponseDto mapToResponseDto(Parcel parcel) {
        return ParcelResponseDto.builder()
                .id(parcel.getId())
                .senderName(parcel.getSenderName())
                .senderPhone(parcel.getSenderPhone())
                .receiverName(parcel.getReceiverName())
                .receiverPhone(parcel.getReceiverPhone())
                .destination(parcel.getDestination())
                .description(parcel.getDescription())
                .cost(parcel.getCost())
                .date(parcel.getDate())
                .tripId(parcel.getTrip() != null ? parcel.getTrip().getId() : null)
                .agentId(parcel.getRegisterBy() != null ? parcel.getRegisterBy().getId() : null)
                .build();
    }
}

