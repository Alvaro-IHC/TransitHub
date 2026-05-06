package com.aihc.transithub.vehicle.entities;

import com.aihc.transithub.finance.entities.Contribution;
import com.aihc.transithub.user.entities.Driver;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.YearMonth;
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

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "affiliation_date")
    private LocalDate affiliationDate;

    @ManyToOne
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    private Driver driver;

    @OneToMany(mappedBy = "vehicle", fetch = FetchType.LAZY)
    private List<Contribution> contributions;

    public YearMonth getAffiliationYearMonth() {
        return affiliationDate != null
                ? YearMonth.from(affiliationDate.getDayOfMonth() > 7
                    ? affiliationDate.plusMonths(1)
                    : affiliationDate)
                : null;
    }

    public List<YearMonth> getYearMonths() {
        return contributions.stream().map(Contribution::getYearMonth).toList();
    }
}
