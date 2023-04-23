package org.pl.gateway.module.authentication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.gateway.module.model.ClientRest;
import org.pl.gateway.module.model.UserRestCredentials;
import org.pl.gateway.module.model.exceptions.RepositoryRestException;
import org.pl.gateway.module.model.exceptions.authentication.InvalidCredentialsException;
import org.pl.gateway.module.model.exceptions.authentication.UserIsArchiveException;
import org.pl.gateway.module.model.exceptions.authentication.UserNotFoundException;
import org.pl.gateway.module.ports.userinterface.client.ReadClientUseCases;

@ApplicationScoped
public class UserAuthenticator {
    @Inject
    private ReadClientUseCases readClientUseCases;

    public ClientRest authenticate(UserRestCredentials userCredentials) throws InvalidCredentialsException, UserIsArchiveException, UserNotFoundException {
        try {
            ClientRest client = readClientUseCases.getClientByUsername(userCredentials.getUsername());
            if (client.getArchive()) {
                throw new UserIsArchiveException();
            }
            if (!client.getPassword().equals(userCredentials.getPassword())) {
                throw new InvalidCredentialsException();
            }
            return client;
        } catch (RepositoryRestException e) {
            // No user was found
            throw new UserNotFoundException();
        }
    }
}
