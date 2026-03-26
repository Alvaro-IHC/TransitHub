package com.aihc.transithub.user.dtos;

import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * DTO response that represents a driver instance.
 *
 * @author Alvaro Huanca
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DriverResponseDto extends UserResponseDto {
    private String drivingLicense;
    private String category;
    private String type;
}
