package com.aihc.transithub.user.repositories;

import com.aihc.transithub.user.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * DAO for CRUD operations on Admin entities.
 *
 * @author Alvaro Huanca
 */
@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByUsername(String username);
    boolean existsByUsername(String username);
}
