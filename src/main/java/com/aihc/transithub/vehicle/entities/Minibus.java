package com.aihc.transithub.vehicle.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

/**
 * The Minibus class represents a specific type of vehicle, which is a minibus.
 * It extends the Vehicle class.
 *
 * @author Alvaro Huanca
 */
@Data
@Entity
@Table(name = "minibuses")
public class Minibus extends Vehicle {

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private MinibusStatus status;

}