package org.pl.user.module.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metric;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.pl.user.module.infrastructure.user.ReadUserPort;
import org.pl.user.module.infrastructure.user.WriteUserPort;
import org.pl.user.module.model.User;
import org.pl.user.module.model.exceptions.RepositoryException;
import org.pl.user.module.model.exceptions.UserException;
import org.pl.user.module.userinterface.user.ReadUserUseCases;
import org.pl.user.module.userinterface.user.WriteUserUseCases;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
@Counted(name = "UserService_Counter")
public class UserService implements WriteUserUseCases, ReadUserUseCases {
    @Inject
    @Metric(name = "UserService.readUserPort_Metric")
    private ReadUserPort readUserPort;
    @Inject
    @Metric(name = "UserService.writeUserPort_Metric")
    private WriteUserPort writeUserPort;
    @Inject
    @Metric
    private MetricRegistry metricRegistry;

    @Override
    @Timed(name = "UserService.getUserByUsername_Timer")
    @Counted(name = "UserService.getUserByUsername_Counter")
    public User getUserByUsername(String username) throws RepositoryException {
        return readUserPort.getUserByUsername(username);
    }

    @Override
    @Gauge(unit = "number", name = "UserService.getAllUsers_Gauge")
    @Timed(name = "UserService.getAllUsers_Timer")
    @Counted(name = "UserService.getAllUsers_Counter")
    public List<User> getAllUsers() {
        return readUserPort.getAllUsers();
    }

    @Override
    @Gauge(unit = "number", name = "UserService.getPresentSize_Gauge")
    @Timed(name = "UserService.getPresentSize_Timer")
    @Counted(name = "UserService.getPresentSize_Counter")
    public int getPresentSize() {
        return readUserPort.getUserList(false).size();
    }

    @Override
    @Gauge(unit = "number", name = "UserService.getArchiveSize_Gauge")
    @Timed(name = "UserService.getArchiveSize_Timer")
    @Counted(name = "UserService.getArchiveSize_Counter")
    public int getArchiveSize() {
        return readUserPort.getUserList(true).size();
    }

    @Override
    @Timed(name = "UserService.getAllUsersFilter_Timer")
    @Counted(name = "UserService.getAllUsersFilter_Counter")
    public List<User> getAllUsersFilter(String substr) {
        return readUserPort.getAllUsersFilter(substr);
    }

    @Override
    @Timed(name = "UserService.getUsersByUsername_Timer")
    @Counted(name = "UserService.getUsersByUsername_Counter")
    public List<User> getUsersByUsername(String username) {
        return readUserPort.getUsersByUsername(username);
    }

    @Override
    @Counted(name = "UserService.get_Counter")
    @Timed(name = "UserService.getUsersByUsername_Timer")
    public User get(UUID id) throws RepositoryException {
        return readUserPort.getUser(id);
    }

    @Override
    @Timed(name = "UserService.getInfo_Timer")
    @Counted(name = "UserService.getInfo_Counter")
    public String getInfo(UUID id) throws RepositoryException {
        return readUserPort.getUser(id).toString();
    }

    @Override
    @Counted(name = "UserService.isUserArchive_Counter")
    public boolean isUserArchive(UUID uuid) throws RepositoryException {
        return readUserPort.getUser(uuid).getArchive();
    }

    @Override
    @Counted(name = "UserService.add_Counter")
    @Timed(name = "UserService.add_Timer")
    public User add(User user) throws UserException, RepositoryException {
        if (Objects.equals(user.getFirstName(), "")) {
            metricRegistry.counter("UserService.add.UserFirstNameException_Counter").inc();
            throw new UserException(UserException.USER_FIRST_NAME_EXCEPTION);
        }
        if (Objects.equals(user.getLastName(), "")) {
            metricRegistry.counter("UserService.add.UserLastNameException_Counter").inc();
            throw new UserException(UserException.USER_LAST_NAME_EXCEPTION);
        }
        if (Objects.equals(user.getPhoneNumber(), "")) {
            metricRegistry.counter("UserService.add.UserPhoneNumberException_Counter").inc();
            throw new UserException(UserException.USER_PHONE_NUMBER_EXCEPTION);
        }
        if (Objects.equals(user.getAddress(), null)) {
            metricRegistry.counter("UserService.add.UserAddressException_Counter").inc();
            throw new UserException(UserException.USER_ADDRESS_EXCEPTION);
        }
        return writeUserPort.createUser(user);
    }

    @Override
    @Counted(name = "UserService.archive_Counter")
    public User archive(UUID uuid) throws RepositoryException {
        return writeUserPort.deleteUser(uuid);
    }

    @Override
    @Counted(name = "UserService.updateUser_Counter")
    @Timed(name = "UserService.updateUser_Timer")
    public User updateUser(UUID uuid, User user) throws RepositoryException {
        return writeUserPort.updateUser(uuid, user);
    }

    @Override
    @Counted(name = "UserService.changePassword_Counter")
    public User updatePassword(UUID uuid, String newPassword) throws RepositoryException {
        return writeUserPort.changePassword(uuid, newPassword);
    }

    @Override
    @Counted(name = "UserService.unarchive_Counter")
    public User dearchive(UUID uuid) throws RepositoryException {
        return writeUserPort.restoreUser(uuid);
    }
}
