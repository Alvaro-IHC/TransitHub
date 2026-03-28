package com.aihc.transithub.travel.repositories;

import com.aihc.transithub.travel.entities.Trip;
import com.aihc.transithub.travel.enums.TripStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Trip entity operations.
 *
 * @author Alvaro Huanca
 */
@Repository
public interface TripRepository extends JpaRepository<Trip, UUID> {
    List<Trip> findByStatus(TripStatus status);
}
