package org.pl.adapter.data.model.exceptions;

import lombok.ToString;

@ToString
public class AddressEntException extends Exception {
    public static final String CITY_EXCEPTION = "Invalid city was given.";
    public static final String NUMBER_EXCEPTION = "Invalid number was given.";
    public static final String STREET_EXCEPTION = "Invalid street was given.";

    public AddressEntException(String message) {
        super(message);
    }
}
