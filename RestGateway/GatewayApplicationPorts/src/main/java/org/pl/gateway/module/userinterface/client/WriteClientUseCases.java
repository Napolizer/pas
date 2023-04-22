package org.pl.gateway.module.userinterface.client;


import org.pl.gateway.module.model.Client;
import org.pl.gateway.module.model.exceptions.ClientException;
import org.pl.gateway.module.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteClientUseCases {
    Client add(Client client) throws RepositoryException, ClientException;
    Client archive(UUID id) throws RepositoryException;
    Client updateClient(UUID id, Client client) throws RepositoryException;
    Client updatePassword(UUID id, String newPassword) throws RepositoryException;
    Client dearchive(UUID id) throws RepositoryException;

}