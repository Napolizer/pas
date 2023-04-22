package org.pl.gateway.module.infrastructure.client;

import org.pl.repair.module.model.Client;
import org.pl.repair.module.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteClientPort {
    Client createClient(Client client) throws RepositoryException;
    Client deleteClient(UUID uuid) throws RepositoryException;
    Client restoreClient(UUID uuid) throws RepositoryException;
    Client updateClient(UUID uuid, Client client) throws RepositoryException;

}
