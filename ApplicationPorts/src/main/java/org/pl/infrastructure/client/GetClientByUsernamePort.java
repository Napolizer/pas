package org.pl.infrastructure.client;

import org.pl.model.exceptions.RepositoryException;
import org.pl.model.Client;

public interface GetClientByUsernamePort {
    Client getClientByUsername(String username) throws RepositoryException;
}
