package com.aihc.transithub.user.repositories;

import com.aihc.transithub.user.entities.Treasurer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * DAO for CRUD operations on Treasurer entities.
 *
 * @author Alvaro Huanca
 */
@Repository
public interface TreasurerRepository extends JpaRepository<Treasurer, UUID> {
    Optional<Treasurer> findByUsername(String username);
    boolean existsByUsername(String username);
}
