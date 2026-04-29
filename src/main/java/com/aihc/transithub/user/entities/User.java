package com.aihc.transithub.user.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

/**
 * The User class represents a user in the system. It serves as a base class for different types of users, such as
 * drivers and ticket agent. The class includes common attributes like username, password, name, last name, email,
 * and phone number. It uses JPA annotations to define the entity and its mapping to the database table.
 *
 * @author Alvaro Huanca
 */
@Data
@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    private String name;

    @Column(name = "last_name")
    private String lastName;

    private String email;

    private String phone;

    @Column(name = "photo_url")
    private String photoUrl;
}