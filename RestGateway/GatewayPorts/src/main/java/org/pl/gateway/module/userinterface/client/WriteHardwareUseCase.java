package org.pl.gateway.module.userinterface.client;

import org.pl.repair.module.model.Client;
import org.pl.repair.module.model.exceptions.ClientException;
import org.pl.repair.module.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteHardwareUseCase {
    Client add(Client client) throws RepositoryException, ClientException;
    Client archive(UUID id) throws RepositoryException;
    Client updateClient(UUID id, Client client) throws RepositoryException;
    Client dearchive(UUID id) throws RepositoryException;

}
