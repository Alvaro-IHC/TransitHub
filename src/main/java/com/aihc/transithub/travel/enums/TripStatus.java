package com.aihc.transithub.travel.enums;

import lombok.Getter;

/**
 * Enumeration for trip status in the system.
 *
 * @author Alvaro Huanca
 */
@Getter
public enum TripStatus {
    NOT_DEPARTED("Not Departed"),
    DEPARTED("Departed"),
    COMPLETED("Completed");

    private final String description;

    TripStatus(String description) {
        this.description = description;
    }

}
