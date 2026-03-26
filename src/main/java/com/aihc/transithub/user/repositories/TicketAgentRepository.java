package com.aihc.transithub.user.repositories;

import com.aihc.transithub.user.entities.TicketAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * DAO for CRUD operations on Ticket Agent entities.
 *
 * @author Alvaro Huanca
 */
@Repository
public interface TicketAgentRepository extends JpaRepository<TicketAgent, UUID> {
    Optional<TicketAgent> findByUsername(String username);
    boolean existsByUsername(String username);
}

