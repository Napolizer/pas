package org.pl.infrastructure.client;

import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;

public interface AddClientPort {
    Client createClient(Client client) throws Exception ;
}
