package org.pl.exceptions;

import lombok.ToString;

@ToString
public class ClientException extends Exception {
    public static final String CLIENT_FIRST_NAME_EXCEPTION = "Invalid name was given.";
    public static final String CLIENT_IDE_EXCEPTION = "Invalid ID was given.";
    public static final String CLIENT_LAST_NAME_EXCEPTION = "Invalid last name was given.";
    public static final String CLIENT_PHONE_NUMBER_EXCEPTION = "Invalid phone number was given.";
    public static final String CLIENT_ADDRESS_EXCEPTION = "Invalid address was given.";
    public static final String CLIENT_TYPE_EXCEPTION = "Invalid client type was given.";
    public static final String CLIENT_TYPE_CALCULATE_DISCOUNT_EXCEPTION = "Cannot calculate discount for negative price.";

    public ClientException(String message) {
        super(message);
    }
}
