package com.aihc.transithub.travel.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

/**
 * Data Transfer Object for updating an existing parcel in the system.
 *
 * @author Alvaro Huanca
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParcelUpdateDto {
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
