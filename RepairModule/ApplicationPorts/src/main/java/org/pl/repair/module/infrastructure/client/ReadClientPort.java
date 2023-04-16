package org.pl.repair.module.infrastructure.client;

import org.pl.repair.module.model.Client;
import org.pl.repair.module.model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface ReadClientPort {
    List<Client> getAllClientsFilter(String substr);
    List<Client> getAllClients();
    Client getClientByUsername(String username) throws RepositoryException;
    List<Client> getClientList(boolean condition);
    Client getClient(UUID uuid) throws RepositoryException;
    List<Client> getClientsByUsername(String username);
}
