package org.pl.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Client_;

import java.util.ArrayList;
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

    public void deleteClient(UUID id) throws RepositoryException {
        try {
            Client client = entityManager.find(Client.class, id);
                if (!client.isArchive()) {
                    entityManager.getTransaction().begin();
                    entityManager.merge(client);
                    client.setArchive(true);
                    entityManager.getTransaction().commit();
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
        List<Client> clients = new ArrayList<>();
        try (Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilder criteriaBuilder = (CriteriaBuilder) session.getCriteriaBuilder();
            CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);
            criteriaQuery.select(root);
            Query<Client> query = session.createQuery((CriteriaUpdate) criteriaQuery);
            clients = query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return client;
    }
}