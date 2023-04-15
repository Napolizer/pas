package org.pl.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.infrastracture.user.ReadUserPort;
import org.pl.infrastracture.user.WriteUserPort;
import org.pl.model.User;
import org.pl.model.exceptions.UserException;
import org.pl.userinterface.user.ReadUserUseCases;
import org.pl.userinterface.user.WriteUserUseCases;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class UserService implements WriteUserUseCases, ReadUserUseCases {
    @Inject
    private ReadUserPort readUserPort;
    @Inject
    private WriteUserPort writeUserPort;

    @Override
    public User getUser(UUID uuid) {
        return readUserPort.getUser(uuid);
    }

    @Override
    public User getUserByUsername(String username) {
        return readUserPort.getUserByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return readUserPort.getAllUsers();
    }

    @Override
    public User add(User user) throws UserException {
        if (Objects.equals(user.getFirstName(), ""))
            throw new UserException(UserException.USER_FIRST_NAME_EXCEPTION);
        if (Objects.equals(user.getLastName(), ""))
            throw new UserException(UserException.USER_LAST_NAME_EXCEPTION);
        if (Objects.equals(user.getPhoneNumber(), ""))
            throw new UserException(UserException.USER_PHONE_NUMBER_EXCEPTION);
        if (Objects.equals(user.getAddress(), null))
            throw new UserException(UserException.USER_ADDRESS_EXCEPTION);
        return writeUserPort.createUser(user);
    }

    @Override
    public User archive(UUID uuid) {
        return writeUserPort.deleteUser(uuid);
    }

    @Override
    public User updateUser(UUID uuid, User user) {
        return writeUserPort.updateUser(uuid, user);
    }

    @Override
    public User updatePassword(UUID uuid, String newPassword) {
        return writeUserPort.changePassword(uuid, newPassword);
    }

    @Override
    public User dearchive(UUID uuid) {
        return writeUserPort.restoreUser(uuid);
    }
}
