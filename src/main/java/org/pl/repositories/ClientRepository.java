package org.pl.repositories;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Client_;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class ClientRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserTransaction userTransaction;

    public Client saveClient(Client client) throws RepositoryException {
        client.setId(UUID.randomUUID());
        client.getClientType().setId(UUID.randomUUID());
        try {
            if (!entityManager.contains(client)) {
                userTransaction.begin();
                entityManager.persist(client);
                userTransaction.commit();
                return client;
            }
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ADD_EXCEPTION);
    }

    public Client getClientById(UUID uuid) throws RepositoryException {
        try {
            Client client = entityManager.find(Client.class, uuid);
            if (client != null) {
                return client;
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
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
            clientToChange.getClientType().setType(client.getClientType().getType());
            clientToChange.getClientType().setFactor(client.getClientType().getFactor());
            clientToChange.getClientType().setMaxRepairs(client.getClientType().getMaxRepairs());
            clientToChange.setClientAccessType(client.getClientAccessType());
            userTransaction.begin();
            entityManager.merge(clientToChange);
            userTransaction.commit();
            return clientToChange;
        } catch (Exception ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public Client deleteClient(UUID id) throws RepositoryException {
        try {
            Client client = getClientById(id);
            if (!client.isArchive()) {
                userTransaction.begin();
                client.setArchive(true);
                entityManager.merge(client);
                userTransaction.commit();
                return client;
            } else {
                throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
            }
        } catch (NullPointerException | NotSupportedException | SystemException | HeuristicRollbackException | HeuristicMixedException | RollbackException ex) {
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

    public List<Client> getAllClientsFilter(String substr) {
        List<Client> clients;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
        Root<Client> root = criteriaQuery.from(Client.class);
        criteriaQuery.where(criteriaBuilder.like(root.get(Client_.ID), "%" + substr + "%"));
        TypedQuery<Client> query = entityManager.createQuery(criteriaQuery);
        clients = query.getResultList();
        return clients;
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
                userTransaction.begin();
                client.setArchive(false);
                entityManager.merge(client);
                userTransaction.commit();
                return client;
            } else {
                throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
            }
        } catch (NullPointerException | NotSupportedException | SystemException | HeuristicRollbackException | HeuristicMixedException | RollbackException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }
}