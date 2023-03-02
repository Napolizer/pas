package org.pl.infrastructure.client;

import org.pl.model.exceptions.RepositoryException;
import org.pl.model.Client;

import java.util.UUID;

public interface GetClientPort {
    Client getClient(UUID uuid) throws RepositoryException;
}
