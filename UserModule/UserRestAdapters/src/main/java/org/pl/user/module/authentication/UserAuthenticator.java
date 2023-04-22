package org.pl.user.module.authentication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.user.module.converters.UserConverter;
import org.pl.user.module.model.UserRest;
import org.pl.user.module.model.UserRestCredentials;
import org.pl.user.module.model.exceptions.RepositoryException;
import org.pl.user.module.model.exceptions.authentication.InvalidCredentialsException;
import org.pl.user.module.model.exceptions.authentication.UserIsArchiveException;
import org.pl.user.module.model.exceptions.authentication.UserNotFoundException;
import org.pl.user.module.userinterface.user.ReadUserUseCases;

@ApplicationScoped
public class UserAuthenticator {
    @Inject
    private ReadUserUseCases readUserUseCases;
    @Inject
    private UserConverter userConverter;

    public UserRest authenticate(UserRestCredentials userCredentials) throws InvalidCredentialsException, UserIsArchiveException, UserNotFoundException {
        try {
            UserRest client = userConverter.convert(readUserUseCases.getUserByUsername(userCredentials.getUsername()));
            if (client.getArchive()) {
                throw new UserIsArchiveException();
            }
            if (!client.getPassword().equals(userCredentials.getPassword())) {
                throw new InvalidCredentialsException();
            }
            return client;
        } catch (RepositoryException e) {
            // No user was found
            throw new UserNotFoundException();
        }
    }
}
