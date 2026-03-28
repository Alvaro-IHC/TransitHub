package com.aihc.transithub.vehicle.repositories;

import com.aihc.transithub.vehicle.entities.Minibus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for Minibus entity operations.
 *
 * @author Alvaro Huanca
 */
@Repository
public interface MinibusRepository extends JpaRepository<Minibus, UUID> {
}
