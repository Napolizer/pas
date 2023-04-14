package org.pl.model.exceptions;

import jakarta.xml.bind.annotation.XmlTransient;
import org.apache.commons.lang3.builder.ToStringBuilder;

@XmlTransient
public class ClientSoapException extends Exception {
    public static final String CLIENT_FIRST_NAME_EXCEPTION = "Invalid name was given.";
    public static final String CLIENT_IDE_EXCEPTION = "Invalid ID was given.";
    public static final String CLIENT_LAST_NAME_EXCEPTION = "Invalid last name was given.";
    public static final String CLIENT_PHONE_NUMBER_EXCEPTION = "Invalid phone number was given.";
    public static final String CLIENT_ADDRESS_EXCEPTION = "Invalid address was given.";
    public static final String CLIENT_TYPE_EXCEPTION = "Invalid client type was given.";
    public static final String CLIENT_TYPE_CALCULATE_DISCOUNT_EXCEPTION = "Cannot calculate discount for negative price.";

    public ClientSoapException(String message) {
        super(message);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .toString();
    }
}
