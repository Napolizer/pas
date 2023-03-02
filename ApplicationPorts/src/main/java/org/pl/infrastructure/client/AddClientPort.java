package org.pl.infrastructure.client;

import org.pl.model.exceptions.RepositoryException;
import org.pl.model.Client;

public interface AddClientPort {
    Client createClient(Client client) throws RepositoryException;
}
