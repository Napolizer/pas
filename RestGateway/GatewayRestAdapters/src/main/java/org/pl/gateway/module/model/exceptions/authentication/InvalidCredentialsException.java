package org.pl.gateway.module.model.exceptions.authentication;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Provided credentials are invalid");
    }
}
