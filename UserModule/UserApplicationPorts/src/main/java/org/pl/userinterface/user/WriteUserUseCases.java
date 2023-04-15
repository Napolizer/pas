package org.pl.userinterface.user;

import org.pl.model.User;
import org.pl.model.exceptions.UserException;

import java.util.UUID;

public interface WriteUserUseCases {
    User add(User user) throws UserException;
    User archive(UUID uuid);
    User updateUser(UUID uuid, User user);
    User updatePassword(UUID uuid, String newPassword);
    User dearchive(UUID uuid);
}
