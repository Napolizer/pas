package org.pl.exceptions;

import lombok.ToString;

@ToString
public class RepairException extends Exception {
    public static final String REPAIR_CLIENT_EXCEPTION = "Invalid client was given.";
    public static final String REPAIR_HARDWARE_EXCEPTION = "Invalid hardware was given.";
    public static final String REPAIR_ID_EXCEPTION = "Invalid ID was given.";

    public RepairException(String message) {
        super(message);
    }
}
