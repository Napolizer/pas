package org.pl.repair.module.model.exceptions.authentication;

public class UserNotFoundException extends Exception {
    public UserNotFoundException() {
        super("Given user does not exist");
    }
}
