package com.aihc.transithub.vehicle.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * The DumpTruck class represents a dump truck vehicle in the system.
 *
 * @author Alvaro Huanca
 */
@Getter
@Setter
@Entity
@Table(name = "dump_trucks")
public class DumpTruck extends Vehicle {
}