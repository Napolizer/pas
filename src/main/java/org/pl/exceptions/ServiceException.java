package org.pl.exceptions;

import lombok.ToString;

@ToString
public class ServiceException extends Exception {
    public static final String HARDWARE_SERVICE_INVALID_CONDITION_EXCEPTION = "Trying to add object with invalid condition.";
    public static final String HARDWARE_SERVICE_INVALID_HARDWARE_EXCEPTION = "Trying to add object with invalid type.";

    public ServiceException(String message) {
        super(message);
    }
}
