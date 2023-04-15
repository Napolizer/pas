package org.pl.infrastructure.client;

import org.pl.model.Client;
import org.pl.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteClientPort {
    Client createClient(Client client) throws RepositoryException;
    Client deleteClient(UUID uuid) throws RepositoryException;
    Client restoreClient(UUID uuid) throws RepositoryException;
    Client updateClient(UUID uuid, Client client) throws RepositoryException;

}
