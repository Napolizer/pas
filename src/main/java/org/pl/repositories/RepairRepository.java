package org.pl.repositories;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Client_;
import org.pl.model.Repair;
import org.pl.model.Repair_;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class RepairRepository {

    private EntityManager entityManager;

    public Repair saveRepair(Repair repair) throws RepositoryException {
        if (!entityManager.contains(repair)) {
            entityManager.getTransaction().begin();
            entityManager.persist(repair);
            entityManager.getTransaction().commit();
            return repair;
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ADD_EXCEPTION);
    }

    public Repair getRepairById(UUID uuid) throws RepositoryException {
        try {
            Repair repair = entityManager.find(Repair.class, uuid);
            if (repair != null) {
                return repair;
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
        return null;
    }

    public void deleteRepair(UUID id) throws RepositoryException {
        try {
            Repair repair = entityManager.find(Repair.class, id);
            if (!repair.isArchive()) {
                entityManager.getTransaction().begin();
                entityManager.merge(repair);
                repair.setArchive(true);
                entityManager.getTransaction().commit();
            } else {
                throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public List<Repair> getClientRepairs(UUID clientId) {
        List<Repair> repairs;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Repair> criteriaQuery = criteriaBuilder.createQuery(Repair.class);
        Root<Repair> root = criteriaQuery.from(Repair.class);
        Join<Repair, Client> client = root.join(Repair_.CLIENT);
        criteriaQuery.where(criteriaBuilder.equal(client.get(Client_.ID), clientId));
        TypedQuery<Repair> query = entityManager.createQuery(criteriaQuery);
        repairs = query.getResultList();
        return repairs;
    }
}
