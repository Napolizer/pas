package org.pl.user.module.userinterface.user;

import org.pl.user.module.model.User;
import org.pl.user.module.model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface ReadUserUseCases {
    User get(UUID id) throws RepositoryException;
    String getInfo(UUID id) throws RepositoryException;
    boolean isUserArchive(UUID id) throws RepositoryException;
    List<User> getAllUsers();
    int getPresentSize();
    int getArchiveSize();
    List<User> getAllUsersFilter(String substr);
    List<User> getUsersByUsername(String username);
    User getUserByUsername(String username) throws RepositoryException;
}
