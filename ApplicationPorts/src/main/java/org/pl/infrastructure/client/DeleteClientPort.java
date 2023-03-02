package org.pl.infrastructure.client;

import java.util.UUID;

import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;


public interface DeleteClientPort {
    Client deleteClient(UUID uuid) throws RepositoryException;
}
