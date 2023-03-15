package org.pl.userinterface.client;

import org.pl.model.Client;
import org.pl.model.exceptions.ClientException;
import org.pl.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteClientQueries {
    Client add(Client client) throws RepositoryException, ClientException;
    Client archive(UUID id) throws RepositoryException;
    Client updateClient(UUID id, Client client) throws RepositoryException;
    Client updatePassword(UUID id, String newPassword) throws RepositoryException;
    Client dearchive(UUID id) throws RepositoryException;

}
