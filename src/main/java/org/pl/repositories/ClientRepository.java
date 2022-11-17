package org.pl.repositories;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;

import java.util.UUID;

@AllArgsConstructor
public class ClientRepository {
    private EntityManager entityManager;

    public Client saveClient(Client client) throws RepositoryException {
        if (!entityManager.contains(client)) {
            entityManager.persist(client);
            return client;
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ADD_EXCEPTION);
    }

    public Client getClientById(UUID uuid) throws RepositoryException {
        try {
            Client client = entityManager.find(Client.class, uuid);
            if (client != null) {
                return client;
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
        return null;
    }

    public void deleteClient(UUID id) throws RepositoryException {
        try {
            Client client = entityManager.find(Client.class, id);
                if (!client.isArchive()) {
                    entityManager.merge(client);
                    client.setArchive(true);
                } else {
                    throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
                }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }
}