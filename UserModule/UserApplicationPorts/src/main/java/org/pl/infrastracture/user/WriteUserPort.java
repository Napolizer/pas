package org.pl.infrastracture.user;

import org.pl.model.User;

import java.util.UUID;

public interface WriteUserPort {
    User createUser(User user);
    User changePassword(UUID uuid, String newPassword);
    User deleteUser(UUID uuid);
    User restoreUser(UUID uuid);
    User updateUser(UUID uuid, User user);
}
