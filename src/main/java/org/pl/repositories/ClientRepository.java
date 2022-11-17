package org.pl.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaUpdate;
import lombok.AllArgsConstructor;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
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

    public Client getCLientByUsername(String username) {
        Client client = new Client();
        try (Session session = entityManager.unwrap(Session.class)) {
            CriteriaBuilder criteriaBuilder = (CriteriaBuilder) session.getCriteriaBuilder();
            CriteriaQuery<Client> criteriaQuery = criteriaBuilder.createQuery(Client.class);
            Root<Client> root = criteriaQuery.from(Client.class);
            criteriaQuery.select(root).where(criteriaBuilder.equal(root.get("username"), username));
            Query<Client> query = session.createQuery((CriteriaUpdate) criteriaQuery);
            client = query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return client;
    }
}