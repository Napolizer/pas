package org.pl.user.module.model.exceptions;

public class UserException extends Exception {
    public static final String USER_FIRST_NAME_EXCEPTION = "Invalid name was given.";
    public static final String USER_LAST_NAME_EXCEPTION = "Invalid last name was given.";
    public static final String USER_PHONE_NUMBER_EXCEPTION = "Invalid phone number was given.";
    public static final String USER_ADDRESS_EXCEPTION = "Invalid address was given.";
    public static final String USER_CALCULATE_DISCOUNT_EXCEPTION = "Cannot calculate discount for negative price.";

    public UserException(String message) {
        super(message);
    }
}
