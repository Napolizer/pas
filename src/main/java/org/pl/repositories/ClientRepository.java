package org.pl.repositories;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;

import java.util.UUID;

@AllArgsConstructor
public class ClientRepository {
    private EntityManager entityManager;

    public Client saveClient(Client client) {
        if (!entityManager.contains(client)) {
            entityManager.persist(client);
        } else {
            entityManager.merge(client);
        }
        return client;
    }

    public Client getClientById(UUID uuid) throws RepositoryException {
        Client client = entityManager.find(Client.class, uuid);
        if (client != null) {
            return client;
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public void deleteClient(UUID id) throws RepositoryException {
        entityManager.getTransaction().begin();
        Client client = entityManager.find(Client.class, id);
        if (client != null) {
            if (!client.isArchive()) {
                entityManager.merge(client);
                client.setArchive(true);
                entityManager.getTransaction().commit();
                return;
            }
            throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }
}