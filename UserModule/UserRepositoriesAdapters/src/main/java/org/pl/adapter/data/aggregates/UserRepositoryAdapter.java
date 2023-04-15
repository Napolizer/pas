package org.pl.adapter.data.aggregates;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.adapter.data.converters.UserConverter;
import org.pl.adapter.data.model.UserEnt;
import org.pl.adapter.data.model.exceptions.RepositoryEntException;
import org.pl.adapter.data.repositories.UserEntRepository;
import org.pl.infrastracture.user.ReadUserPort;
import org.pl.infrastracture.user.WriteUserPort;
import org.pl.model.User;
import org.pl.model.exceptions.RepositoryException;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class UserRepositoryAdapter implements ReadUserPort, WriteUserPort {
    @Inject
    UserEntRepository userEntRepository;
    @Inject
    UserConverter userConverter;

    private UserEnt convert(User user) {
        return userConverter.convert(user);
    }

    private User convert(UserEnt userEnt) {
        return userConverter.convert(userEnt);
    }

    @Override
    public User createUser(User user) throws RepositoryException {
        try {
            return convert(userEntRepository.saveUser(convert(user)));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public User changePassword(UUID uuid, String newPassword) throws RepositoryException {
        try {
            return convert(userEntRepository.changePassword(uuid, newPassword));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public User deleteUser(UUID uuid) throws RepositoryException {
        try {
            return convert(userEntRepository.deleteUser(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<User> getAllUsersFilter(String substr) {
        List<User> userList = new ArrayList<>();
        for (UserEnt user : userEntRepository.getAllUsersFilter(substr)) {
            userList.add(convert(user));
        }
        return userList;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        for (UserEnt user : userEntRepository.getAllUsers()) {
            usersList.add(convert(user));
        }
        return usersList;
    }

    @Override
    public User getUserByUsername(String username) throws RepositoryException {
        try {
            return convert(userEntRepository.getUserByUsername(username));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<User> getUserList(boolean condition) {
        List<User> usersList = new ArrayList<>();
        for (UserEnt user : userEntRepository.getUsers(condition)) {
            usersList.add(convert(user));
        }
        return usersList;
    }

    @Override
    public User getUser(UUID uuid) throws RepositoryException {
        try {
            return convert(userEntRepository.getUserById(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<User> getUsersByUsername(String username) {
        List<User> usersList = new ArrayList<>();
        for (UserEnt user : userEntRepository.getUsersByUsername(username)) {
            usersList.add(convert(user));
        }
        return usersList;
    }

    @Override
    public User restoreUser(UUID uuid) throws RepositoryException {
        try {
            return convert(userEntRepository.restoreUser(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public User updateUser(UUID uuid, User user) throws RepositoryException {
        try {
            return convert(userEntRepository.updateUser(uuid, convert(user)));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
