package org.pl.infrastracture.user;

import org.pl.model.User;
import org.pl.model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface ReadUserPort {
    List<User> getAllUsersFilter(String substr);
    List<User> getAllUsers();
    User getUserByUsername(String username) throws RepositoryException;
    List<User> getUserList(boolean condition);
    User getUser(UUID uuid) throws RepositoryException;
    List<User> getUsersByUsername(String username);
}
