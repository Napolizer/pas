package org.pl.user.module.model.exceptions.authentication;

public class UserIsArchiveException extends Exception {
    public UserIsArchiveException() {
        super("Cannot authenticate archived user");
    }
}
