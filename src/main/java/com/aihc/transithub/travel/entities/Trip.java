package com.aihc.transithub.travel.entities;

import com.aihc.transithub.vehicle.entities.Minibus;
import com.aihc.transithub.travel.enums.TripStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

/**
 * The Trip class represents a trip in the system. It contains information about the destination, date, time, etc.
 *
 * @author Alvaro Huanca
 */
@Data
@Entity
@Table(name = "trips")
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String destination;

    private LocalDate date;

    private LocalTime time;

    @Column(name = "driver_name")
    private String driverName;

//    @ManyToOne
//    @JoinColumn(name = "driver_id")
//    private Driver driver;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TripStatus status = TripStatus.NOT_DEPARTED;

    @ManyToOne
    @JoinColumn(name = "minibus_id")
    private Minibus minibus;

    @OneToMany(mappedBy = "trip")
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "trip")
    private List<Parcel> parcels;

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

}
