package org.pl.userinterface.user;

import org.pl.model.User;

import java.util.List;
import java.util.UUID;

public interface ReadUserUseCases {
    User getUser(UUID uuid);
    User getUserByUsername(String username);
    List<User> getAllUsers();
}
