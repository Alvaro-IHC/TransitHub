package com.aihc.transithub.travel.services;

import com.aihc.transithub.travel.dtos.TripCreateDto;
import com.aihc.transithub.travel.dtos.TripResponseDto;
import com.aihc.transithub.travel.dtos.TripUpdateDto;
import com.aihc.transithub.travel.entities.Trip;
import com.aihc.transithub.travel.enums.TripStatus;
import com.aihc.transithub.travel.repositories.TripRepository;
import com.aihc.transithub.travel.websocket.TravelWebSocketHandler;
import com.aihc.transithub.travel.websocket.TravelWebSocketMessage;
import com.aihc.transithub.travel.websocket.WebSocketEntityType;
import com.aihc.transithub.travel.websocket.WebSocketEventType;
import com.aihc.transithub.vehicle.entities.Minibus;
import com.aihc.transithub.vehicle.repositories.MinibusRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing trip operations in the system.
 *
 * @author Alvaro Huanca
 */
@Service
public class TripService {

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private MinibusRepository minibusRepository;

    @Autowired
    private TravelWebSocketHandler webSocketHandler;

    /**
     * Create a new trip
     */
    public TripResponseDto createTrip(TripCreateDto tripCreateDto) {
        validateTripCreate(tripCreateDto);

        Trip trip = new Trip();
        trip.setDestination(tripCreateDto.getDestination());
        trip.setDate(tripCreateDto.getDate());
        trip.setTime(LocalTime.parse(tripCreateDto.getTime()));
        trip.setDriverName(tripCreateDto.getDriverName());
        trip.setStatus(TripStatus.NOT_DEPARTED);

        Minibus minibus = minibusRepository.findById(tripCreateDto.getMinibusId())
                .orElseThrow(() -> new IllegalArgumentException("Minibus not found with ID: " + tripCreateDto.getMinibusId()));
        trip.setMinibus(minibus);

        Trip savedTrip = tripRepository.save(trip);
        return mapToResponseDto(savedTrip);
    }

    /**
     * Get a trip by ID
     */
    public TripResponseDto getTripById(UUID id) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with ID: " + id));
        return mapToResponseDto(trip);
    }

    /**
     * Get all trips
     */
    public List<TripResponseDto> getAllTrips() {
        return tripRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Get trips by status
     */
    public List<TripResponseDto> getTripsByStatus(TripStatus status) {
        return tripRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    /**
     * Update a trip
     */
    public TripResponseDto updateTrip(UUID id, TripUpdateDto tripUpdateDto) {
        Trip trip = tripRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Trip not found with ID: " + id));

        if (tripUpdateDto.getDestination() != null) {
            trip.setDestination(tripUpdateDto.getDestination());
        }
        if (tripUpdateDto.getDate() != null) {
            trip.setDate(tripUpdateDto.getDate());
        }
        if (tripUpdateDto.getTime() != null) {
            trip.setTime(LocalTime.parse(tripUpdateDto.getTime()));
        }
        if (tripUpdateDto.getDriverName() != null) {
            trip.setDriverName(tripUpdateDto.getDriverName());
        }
        if (tripUpdateDto.getStatus() != null) {
            trip.setStatus(tripUpdateDto.getStatus());
        }
        if (tripUpdateDto.getMinibusId() != null) {
            Minibus minibus = minibusRepository.findById(tripUpdateDto.getMinibusId())
                    .orElseThrow(() -> new IllegalArgumentException("Minibus not found with ID: " + tripUpdateDto.getMinibusId()));
            trip.setMinibus(minibus);
        }

        Trip updatedTrip = tripRepository.save(trip);
        TripResponseDto responseDto = mapToResponseDto(updatedTrip);

        // Broadcast WebSocket event
        TravelWebSocketMessage message = TravelWebSocketMessage.builder()
                .eventType(WebSocketEventType.UPDATED)
                .entityType(WebSocketEntityType.TRIP)
                .timestamp(System.currentTimeMillis())
                .build();
        webSocketHandler.broadcastEvent(message);

        return responseDto;
    }

    /**
     * Delete a trip by ID
     */
    public void deleteTrip(UUID id) {
        if (!tripRepository.existsById(id)) {
            throw new IllegalArgumentException("Trip not found with ID: " + id);
        }
        tripRepository.deleteById(id);
    }

    /**
     * Validate data for creating trip
     */
    private void validateTripCreate(TripCreateDto tripCreateDto) {
        if (StringUtils.isBlank(tripCreateDto.getDestination())) {
            throw new IllegalArgumentException("Destination is required");
        }

        if (tripCreateDto.getDate() == null) {
            throw new IllegalArgumentException("Date is required");
        }

        if (tripCreateDto.getTime() == null) {
            throw new IllegalArgumentException("Time is required");
        }

        if (tripCreateDto.getMinibusId() == null) {
            throw new IllegalArgumentException("Minibus ID is required");
        }
    }

    /**
     * Map Trip entity to TripResponseDto
     */
    private TripResponseDto mapToResponseDto(Trip trip) {
        return TripResponseDto.builder()
                .id(trip.getId())
                .destination(trip.getDestination())
                .date(trip.getDate())
                .time(trip.getTime())
                .driverName(trip.getDriverName())
                .status(trip.getStatus())
                .minibusId(trip.getMinibus() != null ? trip.getMinibus().getId() : null)
                .build();
    }
}
