package org.pl.repair.module.model.exceptions.authentication;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Provided credentials are invalid");
    }
}
