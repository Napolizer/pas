package org.pl.exceptions;

import lombok.ToString;

@ToString
public class AddressException extends Exception {
    public static final String CITY_EXCEPTION = "Invalid city was given.";
    public static final String NUMBER_EXCEPTION = "Invalid number was given.";
    public static final String STREET_EXCEPTION = "Invalid street was given.";

    public AddressException(String message) {
        super(message);
    }
}
