package com.aihc.transithub.travel.repositories;

import com.aihc.transithub.travel.entities.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Parcel entity operations.
 *
 * @author Alvaro Huanca
 */
@Repository
public interface ParcelRepository extends JpaRepository<Parcel, UUID> {
    List<Parcel> findByTripId(UUID tripId);
    List<Parcel> findByRegisterById(UUID agentId);
}

