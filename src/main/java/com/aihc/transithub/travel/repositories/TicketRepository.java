package com.aihc.transithub.travel.repositories;

import com.aihc.transithub.travel.entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * Repository interface for Ticket entity operations.
 *
 * @author Alvaro Huanca
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    List<Ticket> findByTripId(UUID tripId);
    List<Ticket> findBySoldBy(UUID agentId);
}
