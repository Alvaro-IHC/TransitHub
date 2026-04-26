package com.aihc.transithub.user.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * The Admin class represents an administrator in the system. It extends the User class.
 *
 * @author Alvaro Huanca
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "admins")
public class Admin extends User {

    private String position;

    @Column(name = "position_start_date")
    private LocalDate positionStartDate;

    @Column(name = "access_level")
    private String accessLevel;
}
