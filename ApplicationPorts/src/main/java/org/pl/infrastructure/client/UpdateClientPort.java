package org.pl.infrastructure.client;

import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;

import java.util.UUID;

public interface UpdateClientPort {
    Client updateClient(UUID uuid, Client client) throws RepositoryException;
}
