package com.aihc.transithub.vehicle.entities;

import com.aihc.transithub.finance.entities.Contribution;
import com.aihc.transithub.user.entities.Driver;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

/**
 * The Vehicle class represents a vehicle in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@Entity
@Table(name = "vehicles")
@Inheritance(strategy = InheritanceType.JOINED)
public class Vehicle {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String ruat;

    private String model;

    private String brand;

    private Integer capacity;

    @Column(name = "licence_plate")
    private String licensePlate;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    private List<Contribution> contributions;
}
