package org.pl.authentication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.converters.ClientConverter;
import org.pl.model.ClientRest;
import org.pl.model.UserRestCredentials;
import org.pl.model.exceptions.RepositoryException;
import org.pl.model.exceptions.authentication.InvalidCredentialsException;
import org.pl.model.exceptions.authentication.UserIsArchiveException;
import org.pl.model.exceptions.authentication.UserNotFoundException;
import org.pl.userinterface.client.ReadClientQueries;

@ApplicationScoped
public class UserAuthenticator {
    @Inject
    private ReadClientQueries readClientQueries;
    @Inject
    private ClientConverter clientConverter;

    public ClientRest authenticate(UserRestCredentials userCredentials) throws InvalidCredentialsException, UserIsArchiveException, UserNotFoundException {
        try {
            ClientRest client = clientConverter.convert(readClientQueries.getClientByUsername(userCredentials.getUsername()));
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
