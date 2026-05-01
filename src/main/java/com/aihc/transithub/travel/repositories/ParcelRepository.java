package com.aihc.transithub.travel.repositories;

import com.aihc.transithub.travel.entities.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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
    
    @Query("SELECT p FROM Parcel p WHERE p.registerBy.id = :agentId AND p.date BETWEEN :startDate AND :endDate")
    List<Parcel> findByRegisterByIdAndDateRange(@Param("agentId") UUID agentId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}

