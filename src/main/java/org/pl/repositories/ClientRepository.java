package org.pl.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Client_;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ClientRepository {
    private EntityManager entityManager;

    public Client saveClient(Client client) throws RepositoryException {
        if (!entityManager.contains(client)) {
            entityManager.getTransaction().begin();
            entityManager.persist(client);
            entityManager.getTransaction().commit();
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

    public Client updateClient(UUID id, Client client) throws RepositoryException {
        try {
            Client clientToChange = entityManager.find(Client.class, id);
            clientToChange.setFirstName(client.getFirstName());
            clientToChange.setLastName(client.getLastName());
            clientToChange.setBalance(client.getBalance());
            clientToChange.setAddress(client.getAddress());
            clientToChange.setPhoneNumber(client.getPhoneNumber());
            clientToChange.setArchive(client.isArchive());
            clientToChange.setUsername(client.getUsername());
            clientToChange.getClientType().setTypeName(client.getClientType().getTypeName());
            clientToChange.getClientType().setFactor(client.getClientType().getFactor());
            clientToChange.getClientType().setMaxRepairs(client.getClientType().getMaxRepairs());
            clientToChange.setClientAccessType(client.getClientAccessType());
            entityManager.getTransaction().begin();
            entityManager.merge(clientToChange);
            entityManager.getTransaction().commit();
            return clientToChange;
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public Client deleteClient(UUID id) throws RepositoryException {
        try {
            Client client = entityManager.find(Client.class, id);
                if (!client.isArchive()) {
                    entityManager.getTransaction().begin();
                    entityManager.merge(client);
                    client.setArchive(true);
                    entityManager.getTransaction().commit();
                    return client;
                } else {
                    throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
                }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public List<Client> getClients(boolean condition) {
        List<Client> clients;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(Client_.ARCHIVE), condition));
        TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
        clients = query.getResultList();
        return clients;
    }

    public List<Client> getAllClients() {
        List<Client> clients;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);
        criteriaQuery.select(root);
        TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
        clients = query.getResultList();
        return clients;
    }

    public Client getClientByUsername(String username) throws RepositoryException {
        Client client;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(Client_.USERNAME), username));
        TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
        try {
            client = query.getSingleResult();
        } catch (NoResultException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_BY_USERNAME_EXCEPTION);
        }
        return client;
    }

    public List<Client> getClientsByUsername(String username) {
        List<Client> clients;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);
        criteriaQuery.where(criteriaBuilder.like(root.get(Client_.USERNAME), "%" + username + "%"));
        TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
        clients = query.getResultList();
        return clients;
    }

    public Client restoreClient(UUID uuid) throws RepositoryException {
        try {
            Client client = entityManager.find(Client.class, uuid);
            if (client.isArchive()) {
                entityManager.getTransaction().begin();
                entityManager.merge(client);
                client.setArchive(false);
                entityManager.getTransaction().commit();
                return client;
            } else {
                throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }
}