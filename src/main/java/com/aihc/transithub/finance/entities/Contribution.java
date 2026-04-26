package com.aihc.transithub.finance.entities;

import com.aihc.transithub.user.entities.Treasurer;
import com.aihc.transithub.vehicle.entities.Vehicle;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * The Contribution class represents a financial contribution made by a vehicle and collected by a treasurer.
 *
 * @author Alvaro Huanca
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "contributions")
public class Contribution {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;

    private String concept;

    @Column(name = "receipt_number")
    private String receiptNumber;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "treasurer_id", nullable = false)
    private Treasurer collectedBy;
}
