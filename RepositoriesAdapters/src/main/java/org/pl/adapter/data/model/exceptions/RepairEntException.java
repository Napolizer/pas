package org.pl.adapter.data.model.exceptions;

import lombok.ToString;

@ToString
public class RepairEntException extends Exception {
    public static final String REPAIR_CLIENT_EXCEPTION = "Invalid client was given.";
    public static final String REPAIR_HARDWARE_EXCEPTION = "Invalid hardware was given.";

    public RepairEntException(String message) {
        super(message);
    }
}
