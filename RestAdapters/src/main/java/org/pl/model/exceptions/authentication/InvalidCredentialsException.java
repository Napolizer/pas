package org.pl.model.exceptions.authentication;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Provided credentials are invalid");
    }
}
