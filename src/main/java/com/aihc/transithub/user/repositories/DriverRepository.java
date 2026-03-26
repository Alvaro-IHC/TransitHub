package com.aihc.transithub.user.repositories;

import com.aihc.transithub.user.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * DAO for CRUD operations on Driver entities.
 *
 * @author Alvaro Huanca
 */
@Repository
public interface DriverRepository extends JpaRepository<Driver, UUID> {
    Optional<Driver> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByDrivingLicense(String drivingLicense);
}

