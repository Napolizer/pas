package org.pl.repair.module.authentication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.repair.module.converters.ClientConverter;
import org.pl.repair.module.model.ClientRest;
import org.pl.repair.module.model.UserRestCredentials;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.repair.module.model.exceptions.authentication.InvalidCredentialsException;
import org.pl.repair.module.model.exceptions.authentication.UserIsArchiveException;
import org.pl.repair.module.model.exceptions.authentication.UserNotFoundException;
import org.pl.gateway.module.userinterface.client.ReadClientUseCases;

@ApplicationScoped
public class UserAuthenticator {
    @Inject
    private ReadClientUseCases readClientUseCases;
    @Inject
    private ClientConverter clientConverter;

    public ClientRest authenticate(UserRestCredentials userCredentials) throws InvalidCredentialsException, UserIsArchiveException, UserNotFoundException {
        try {
            ClientRest client = clientConverter.convert(readClientUseCases.getClientByUsername(userCredentials.getUsername()));
            if (client.getArchive()) {
                throw new UserIsArchiveException();
            }
//            if (!client.getPassword().equals(userCredentials.getPassword())) {
//                throw new InvalidCredentialsException();
//            }
            return client;
        } catch (RepositoryException e) {
            // No user was found
            throw new UserNotFoundException();
        }
    }
}
