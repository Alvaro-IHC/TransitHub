package com.aihc.transithub.finance.repositories;

import com.aihc.transithub.finance.entities.Contribution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}
