package com.aihc.transithub.user.entities;

import com.aihc.transithub.finance.entities.Contribution;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * The Treasurer class represents a treasurer in the system. It extends the User class.
 *
 * @author Alvaro Huanca
 */
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "treasurers")
public class Treasurer extends User {

    @Column(name = "bank_account")
    private String bankAccount;

    @Column(name = "office_number")
    private String officeNumber;

    @Column(name = "position_start_date")
    private LocalDate positionStartDate;

    @OneToMany(mappedBy = "collectedBy", fetch = FetchType.LAZY)
    private List<Contribution> collectedContributions;
}
