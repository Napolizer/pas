package org.pl.repositories;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;

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

    public Repair updateRepair(UUID id, Repair repair) throws RepositoryException {
        try {
            Repair repairToChange = entityManager.find(Repair.class, id);
            repairToChange.setClient(repair.getClient());
            repairToChange.setHardware(repair.getHardware());
            repairToChange.setArchive(repair.isArchive());
            entityManager.getTransaction().begin();
            entityManager.merge(repairToChange);
            entityManager.getTransaction().commit();
            return repairToChange;
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public Repair deleteRepair(UUID id) throws RepositoryException {
        try {
            Repair repair = entityManager.find(Repair.class, id);
            if (!repair.isArchive()) {
                entityManager.getTransaction().begin();
                entityManager.merge(repair);
                repair.setArchive(true);
                entityManager.getTransaction().commit();
                return repair;
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

    public List<Repair> getRepairs(boolean condition) {
        List<Repair> repairs;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Repair> criteriaQuery = criteriaBuilder.createQuery(Repair.class);
        Root<Repair> root = criteriaQuery.from(Repair.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(Repair_.ARCHIVE), condition));
        TypedQuery<Repair> query = entityManager.createQuery(criteriaQuery);
        repairs = query.getResultList();
        return repairs;
    }

    public void repair(UUID id) throws RepositoryException, HardwareException {
        Repair repair = getRepairById(id);
        if (repair.isArchive() || repair.getClient().isArchive() || repair.getHardware().isArchive()) {
            throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
        }
        repair.getHardware().setArchive(true);
        repair.setArchive(true);

        double price = repair.getHardware().getHardwareType().calculateRepairCost(repair.getHardware().getPrice());
        repair.getClient().setBalance(
                repair.getClient().getBalance() - price
        );

        entityManager.getTransaction().begin();
        entityManager.merge(repair);
        entityManager.getTransaction().commit();
    }
}
