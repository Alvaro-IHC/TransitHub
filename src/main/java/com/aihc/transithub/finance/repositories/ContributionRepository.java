package com.aihc.transithub.finance.repositories;

import com.aihc.transithub.finance.entities.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * DAO for CRUD operations on Contribution entities.
 *
 * @author Alvaro Huanca
 */
@Repository
public interface ContributionRepository extends JpaRepository<Contribution, UUID> {
    List<Contribution> findByVehicleId(UUID driverId);
    List<Contribution> findByCollectedById(UUID treasurerId);

    @Query("SELECT c FROM Contribution c " +
            "WHERE c.collectedBy.id = :treasurerId " +
            "AND c.paymentDate BETWEEN :startDate AND :endDate")
    List<Contribution> findByCollectedByIdAndDateRange(
            @Param("treasurerId") UUID treasurerId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);
}
