package org.pl.userinterface.client;

import org.pl.model.Client;
import org.pl.model.ClientType;
import org.pl.model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface ReadClientQueries {
    Client get(UUID id) throws RepositoryException;
    String getInfo(UUID id) throws RepositoryException;
    double getClientBalance(UUID id) throws RepositoryException;
    boolean isClientArchive(UUID id) throws RepositoryException;
    List<Client> getAllClients();
    int getPresentSize();
    int getArchiveSize();
    List<Client> getAllClientsFilter(String substr);
    List<Client> getClientsByUsername(String username);
    ClientType getClientTypeById(UUID id) throws RepositoryException;
    Client getClientByUsername(String username) throws RepositoryException;
}
