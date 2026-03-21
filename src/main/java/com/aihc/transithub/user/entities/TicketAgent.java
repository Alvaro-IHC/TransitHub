package com.aihc.transithub.user.entities;

import com.aihc.transithub.travel.entities.Parcel;
import com.aihc.transithub.travel.entities.Ticket;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The TicketAgent class represents a ticket agent in the system. It extends the User class,
 *
 * @author Alvaro Huanca
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ticket_agents")
public class TicketAgent extends User {

    @Column(name = "counter_number")
    private String counterNumber;

    @Column(name = "terminal_name")
    private String terminalName;

    @Column(name = "shift_start")
    private LocalTime shiftStart;

    @Column(name = "shift_end")
    private LocalTime shiftEnd;

    @Column(name = "position_start_date")
    private LocalDate positionStartDate;

    @OneToMany(mappedBy = "soldBy")
    private List<Ticket> soldTickets;

    @OneToMany(mappedBy = "registerBy")
    private List<Parcel> registerParcels;
}

