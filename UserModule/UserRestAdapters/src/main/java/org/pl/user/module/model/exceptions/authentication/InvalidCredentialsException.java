package org.pl.user.module.model.exceptions.authentication;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Provided credentials are invalid");
    }
}
