package org.pl.infrastracture.user;

import org.pl.model.User;

import java.util.List;
import java.util.UUID;

public interface ReadUserPort {
    List<User> getAllUsersFilter(String substr);
    List<User> getAllUsers();
    User getUserByUsername(String username);
    List<User> getUserList(boolean condition);
    User getUser(UUID uuid);
    List<User> getUsersByUsername(String username);
}
