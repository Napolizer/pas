package org.pl.userinterface.user;

import org.pl.model.User;

import java.util.List;
import java.util.UUID;

public interface ReadUserUseCases {
    User get(UUID id);
    String getInfo(UUID id);
    boolean isUserArchive(UUID id);
    List<User> getAllUsers();
    int getPresentSize();
    int getArchiveSize();
    List<User> getAllUsersFilter(String substr);
    List<User> getUsersByUsername(String username);
    User getUserByUsername(String username);
}
