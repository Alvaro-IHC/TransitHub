package com.aihc.transithub.travel.entities;

import com.aihc.transithub.user.entities.TicketAgent;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * The Ticket class represents a ticket for a trip in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@Entity
@Table(name = "tickets")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "passenger_name")
    private String passengerName;

    @Column(name = "seat_number")
    private Integer seatNumber;

    private BigDecimal cost;

    private String destination;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private TicketAgent soldBy;

}