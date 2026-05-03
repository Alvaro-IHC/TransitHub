package com.aihc.transithub.finance.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * DTO to represent a contribution response.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContributionResponseDto {
    private UUID id;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private String paymentTime;
    private int month;
    private int year;
    private String payer;
    private String concept;
    private String receiptNumber;
    private UUID vehicleId;
    private UUID treasurerId;
}
