package com.aihc.transithub.vehicle.repositories;

import com.aihc.transithub.vehicle.entities.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * Repository interface for Vehicle entity operations.
 *
 * @author Alvaro Huanca
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
}
