package org.pl.user.module.userinterface.user;

import org.pl.user.module.model.User;
import org.pl.user.module.model.exceptions.RepositoryException;
import org.pl.user.module.model.exceptions.UserException;

import java.util.UUID;

public interface WriteUserUseCases {
    User add(User user) throws UserException, RepositoryException;
    User archive(UUID uuid) throws RepositoryException;
    User updateUser(UUID uuid, User user) throws RepositoryException;
    User updatePassword(UUID uuid, String newPassword) throws RepositoryException;
    User dearchive(UUID uuid) throws RepositoryException;
}
