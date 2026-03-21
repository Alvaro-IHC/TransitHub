package com.aihc.transithub.user.entities;

import com.aihc.transithub.vehicle.entities.Vehicle;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

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

    // 'mappedBy' points to the field name in the Vehicle class
    @OneToOne(mappedBy = "driver")
    private Vehicle vehicle;
}