package org.pl.adapter.data.repositories;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.adapter.data.exceptions.RepositoryException;
import org.pl.adapter.data.model.ClientEnt;
import org.pl.adapter.data.model.ClientEnt_;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class ClientEntRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserTransaction userTransaction;

    public ClientEnt saveClient(ClientEnt client) throws RepositoryException {
        client.setId(UUID.randomUUID());
        client.getClientTypeEnt().setId(UUID.randomUUID());
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

    public ClientEnt getClientById(UUID uuid) throws RepositoryException {
        try {
            ClientEnt client = entityManager.find(ClientEnt.class, uuid);
            if (client != null) {
                return client;
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public ClientEnt updateClient(UUID id, ClientEnt client) throws RepositoryException {
        try {
            ClientEnt clientToChange = entityManager.find(ClientEnt.class, id);
            clientToChange.setFirstName(client.getFirstName());
            clientToChange.setLastName(client.getLastName());
            clientToChange.setBalance(client.getBalance());
            clientToChange.setAddressEnt(client.getAddressEnt());
            clientToChange.setPhoneNumber(client.getPhoneNumber());
            clientToChange.setArchive(client.isArchive());
            clientToChange.setUsername(client.getUsername());
            clientToChange.getClientTypeEnt().setType(client.getClientTypeEnt().getType());
            clientToChange.getClientTypeEnt().setFactor(client.getClientTypeEnt().getFactor());
            clientToChange.getClientTypeEnt().setMaxRepairs(client.getClientTypeEnt().getMaxRepairs());
            clientToChange.setClientAccessType(client.getClientAccessType());
            userTransaction.begin();
            entityManager.merge(clientToChange);
            userTransaction.commit();
            return clientToChange;
        } catch (Exception ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public ClientEnt changePassword(UUID id, String newPassword) throws RepositoryException {
        try {
            ClientEnt clientToChange = entityManager.find(ClientEnt.class, id);
            userTransaction.begin();
            clientToChange.setPassword(newPassword);
            entityManager.merge(clientToChange);
            userTransaction.commit();
            return clientToChange;
        } catch (Exception ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public ClientEnt deleteClient(UUID id) throws RepositoryException {
        try {
            ClientEnt client = getClientById(id);
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

    public List<ClientEnt> getClients(boolean condition) {
        List<ClientEnt> clients;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEnt> criteriaQuery = criteriaBuilder.createQuery(ClientEnt.class);
        Root<ClientEnt> root = criteriaQuery.from(ClientEnt.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(ClientEnt_.ARCHIVE), condition));
        TypedQuery<ClientEnt> query = entityManager.createQuery(criteriaQuery);
        clients = query.getResultList();
        return clients;
    }

    public List<ClientEnt> getAllClients() {
        List<ClientEnt> clients;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEnt> criteriaQuery = criteriaBuilder.createQuery(ClientEnt.class);
        Root<ClientEnt> root = criteriaQuery.from(ClientEnt.class);
        criteriaQuery.select(root);
        TypedQuery<ClientEnt> query = entityManager.createQuery(criteriaQuery);
        clients = query.getResultList();
        return clients;
    }

    public ClientEnt getClientByUsername(String username) throws RepositoryException {
        ClientEnt client;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEnt> criteriaQuery = criteriaBuilder.createQuery(ClientEnt.class);
        Root<ClientEnt> root = criteriaQuery.from(ClientEnt.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(ClientEnt_.USERNAME), username));
        TypedQuery<ClientEnt> query = entityManager.createQuery(criteriaQuery);
        try {
            client = query.getSingleResult();
        } catch (NoResultException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_BY_USERNAME_EXCEPTION);
        }
        return client;
    }

    public List<ClientEnt> getAllClientsFilter(String substr) {
        List<ClientEnt> clients;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEnt> criteriaQuery = criteriaBuilder.createQuery(ClientEnt.class);
        Root<ClientEnt> root = criteriaQuery.from(ClientEnt.class);
        criteriaQuery.where(criteriaBuilder.like(root.get(ClientEnt_.ID), "%" + substr + "%"));
        TypedQuery<ClientEnt> query = entityManager.createQuery(criteriaQuery);
        clients = query.getResultList();
        return clients;
    }

    public List<ClientEnt> getClientsByUsername(String username) {
        List<ClientEnt> clients;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ClientEnt> criteriaQuery = criteriaBuilder.createQuery(ClientEnt.class);
        Root<ClientEnt> root = criteriaQuery.from(ClientEnt.class);
        criteriaQuery.where(criteriaBuilder.like(root.get(ClientEnt_.USERNAME), "%" + username + "%"));
        TypedQuery<ClientEnt> query = entityManager.createQuery(criteriaQuery);
        clients = query.getResultList();
        return clients;
    }

    public ClientEnt restoreClient(UUID uuid) throws RepositoryException {
        try {
            ClientEnt client = entityManager.find(ClientEnt.class, uuid);
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