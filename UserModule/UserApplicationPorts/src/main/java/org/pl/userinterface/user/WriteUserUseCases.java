package org.pl.userinterface.user;

import org.pl.model.User;
import org.pl.model.exceptions.RepositoryException;
import org.pl.model.exceptions.UserException;

import java.util.UUID;

public interface WriteUserUseCases {
    User add(User user) throws UserException, RepositoryException;
    User archive(UUID uuid) throws RepositoryException;
    User updateUser(UUID uuid, User user) throws RepositoryException;
    User updatePassword(UUID uuid, String newPassword) throws RepositoryException;
    User dearchive(UUID uuid) throws RepositoryException;
}
