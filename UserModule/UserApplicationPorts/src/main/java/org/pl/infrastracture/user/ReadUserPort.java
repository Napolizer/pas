package org.pl.infrastracture.user;

import org.pl.model.User;

import java.util.List;
import java.util.UUID;

public interface ReadUserPort {
    User getUser(UUID uuid);
    User getUserByUsername(String username);
    List<User> getAllUsers();
}
