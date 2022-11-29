package org.pl.repositories;


import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import jakarta.transaction.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class RepairRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserTransaction userTransaction;
    @Inject
    ClientRepository clientRepository;

    public Repair saveRepair(Repair repair) throws RepositoryException {
        repair.setId(UUID.randomUUID());
        try {
            if (!entityManager.contains(repair)) {
                userTransaction.begin();
                entityManager.persist(repair);
                userTransaction.commit();
                return repair;
            }
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ADD_EXCEPTION);
    }

    public Repair getRepairById(UUID uuid) throws RepositoryException {
        try {
            Repair repair = entityManager.find(Repair.class, uuid);
            if (repair != null && !repair.isArchive()) {
                return repair;
            } else {
                throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public Repair updateRepair(UUID id, Repair repair) throws RepositoryException {
        try {
            Repair repairToChange = getRepairById(id);
            if (repair.getClient() != null) {
                repairToChange.setClient(repair.getClient());
            } if (repair.getHardware() != null) {
                repairToChange.setHardware(repair.getHardware());
            } if (repair.getArchive() != null) {
                repairToChange.setArchive(repair.isArchive());
            } if (repair.getDateRange() != null) {
                if (repair.getDateRange().getStartDate() != null) {
                    repairToChange.getDateRange().setStartDate(repair.getDateRange().getStartDate());
                } if (repair.getDateRange().getEndDate() != null) {
                    repairToChange.getDateRange().setEndDate(repair.getDateRange().getEndDate());
                }
            }
            userTransaction.begin();
            entityManager.merge(repairToChange);
            userTransaction.commit();
            return repairToChange;
        } catch (SystemException | HeuristicRollbackException | HeuristicMixedException | NotSupportedException | RollbackException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public Repair deleteRepair(UUID id) throws RepositoryException {
        try {
            Repair repair = getRepairById(id);
            userTransaction.begin();
            repair.setArchive(true);
            entityManager.merge(repair);
            userTransaction.commit();
            return repair;
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        } catch (NullPointerException | NotSupportedException | SystemException | HeuristicRollbackException |
                 HeuristicMixedException | RollbackException ex) {
            throw new RepositoryException(ex.getMessage());
        }
    }

    public List<Repair> getClientRepairs(UUID clientId) throws RepositoryException {
        if (clientRepository.getClientById(clientId) == null) {
            throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
        }
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

    public List<Repair> getAllRepairs() {
        List<Repair> repairs;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Repair> criteriaQuery = criteriaBuilder.createQuery(Repair.class);
        Root<Repair> root = criteriaQuery.from(Repair.class);
        criteriaQuery.select(root);
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

    public Repair repair(UUID id) throws RepositoryException, HardwareException {
        Repair repair = getRepairById(id);
        if (repair == null || repair.getClient().isArchive() || repair.getHardware().isArchive()) {
            throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
        }
        repair.getHardware().setArchive(true);
        repair.getDateRange().setEndDate(new Date());
        repair.setArchive(true);
//
//        double price = repair.getHardware().getHardwareType().calculateRepairCost(repair.getHardware().getPrice());
//        repair.getClient().setBalance(
//                repair.getClient().getBalance() - price
//        );

        try {
            userTransaction.begin();
            entityManager.merge(repair);
            userTransaction.commit();
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
        return repair;
    }
}
