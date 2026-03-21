package com.aihc.transithub.vehicle.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * The Minibus class represents a specific type of vehicle, which is a minibus. It extends the Vehicle class.
 *
 * @author Alvaro Huanca
 */
@Entity
@Table(name = "minibuses")
public class Minibus extends Vehicle {

}