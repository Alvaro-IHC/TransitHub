package com.aihc.transithub.travel.entities;

import com.aihc.transithub.user.entities.TicketAgent;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * The Parcel class represents a parcel that can be registered and associated with a trip in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@Entity
@Table(name = "parcels")
public class Parcel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "sender_phone")
    private String senderPhone;

    @Column(name = "receiver_name")
    private String receiverName;

    @Column(name = "receiver_phone")
    private String receiverPhone;

    private String destination;

    private String description;

    private BigDecimal cost;

    private LocalDate date;

    @ManyToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @ManyToOne
    @JoinColumn(name = "agent_id")
    private TicketAgent registerBy;
}