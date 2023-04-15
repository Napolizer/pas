package org.pl.infrastracture.user;

import org.pl.model.User;
import org.pl.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteUserPort {
    User createUser(User user) throws RepositoryException;
    User changePassword(UUID uuid, String newPassword) throws RepositoryException;
    User deleteUser(UUID uuid) throws RepositoryException;
    User restoreUser(UUID uuid) throws RepositoryException;
    User updateUser(UUID uuid, User user) throws RepositoryException;
}
