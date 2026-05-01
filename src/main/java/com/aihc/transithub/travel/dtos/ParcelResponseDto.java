package com.aihc.transithub.travel.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object for parcel response in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParcelResponseDto {
    private UUID id;
    private String senderName;
    private String senderPhone;
    private String receiverName;
    private String receiverPhone;
    private String destination;
    private String description;
    private BigDecimal cost;
    private LocalDate date;
    private String time;
    private UUID tripId;
    private UUID agentId;
}

