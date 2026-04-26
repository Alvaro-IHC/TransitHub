package com.aihc.transithub.finance.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO to update a contribution instance.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContributionUpdateDto {
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String concept;
    private String receiptNumber;
    private UUID vehicleId;
    private UUID treasurerId;
}
