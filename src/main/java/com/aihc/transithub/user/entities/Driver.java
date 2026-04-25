package com.aihc.transithub.user.entities;

import com.aihc.transithub.vehicle.entities.Vehicle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * The Driver class represents a driver in the system. It extends the User class,
 *
 * @author Alvaro Huanca
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "drivers")
public class Driver extends User {

    @Column(name = "driving_license")
    private String drivingLicense;

    private String category;

    private String type;

    @OneToMany(mappedBy = "driver", cascade = CascadeType.PERSIST)
    private List<Vehicle> vehicles;
}