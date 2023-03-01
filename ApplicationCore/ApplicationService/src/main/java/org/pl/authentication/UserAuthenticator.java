package org.pl.authentication;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.exceptions.RepositoryException;
import org.pl.exceptions.authentication.InvalidCredentialsException;
import org.pl.exceptions.authentication.UserIsArchiveException;
import org.pl.exceptions.authentication.UserNotFoundException;
import org.pl.model.Client;
import org.pl.model.UserCredentials;
import org.pl.services.ClientService;

@ApplicationScoped
public class UserAuthenticator {
    @Inject
    private ClientService clientService;

    public Client authenticate(UserCredentials userCredentials) throws InvalidCredentialsException, UserIsArchiveException, UserNotFoundException {
        try {
            Client client = clientService.getClientByUsername(userCredentials.getUsername());
            if (client.isArchive()) {
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
