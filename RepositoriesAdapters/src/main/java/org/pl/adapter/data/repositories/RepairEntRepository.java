package org.pl.adapter.data.repositories;


import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.adapter.data.model.ClientEnt;
import org.pl.adapter.data.model.ClientEnt_;
import org.pl.adapter.data.model.RepairEnt;
import org.pl.adapter.data.model.RepairEnt_;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class RepairEntRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserTransaction userTransaction;
    @Inject
    ClientEntRepository clientEntRepository;

    public RepairEnt saveRepair(RepairEnt repair) throws RepositoryException {
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

    public RepairEnt getRepairById(UUID uuid) throws RepositoryException {
        try {
            RepairEnt repair = entityManager.find(RepairEnt.class, uuid);
            if (repair != null && !repair.isArchive()) {
                return repair;
            } else {
                throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public RepairEnt updateRepair(UUID id, RepairEnt repair) throws RepositoryException {
        try {
            RepairEnt repairToChange = getRepairById(id);
            if (repair.getClientEnt() != null) {
                repairToChange.setClientEnt(repair.getClientEnt());
            } if (repair.getHardwareEnt() != null) {
                repairToChange.setHardwareEnt(repair.getHardwareEnt());
            } if (repair.getArchive() != null) {
                repairToChange.setArchive(repair.isArchive());
            } if (repair.getDateRangeEnt() != null) {
                if (repair.getDateRangeEnt().getStartDate() != null) {
                    repairToChange.getDateRangeEnt().setStartDate(repair.getDateRangeEnt().getStartDate());
                } if (repair.getDateRangeEnt().getEndDate() != null) {
                    repairToChange.getDateRangeEnt().setEndDate(repair.getDateRangeEnt().getEndDate());
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

    public RepairEnt deleteRepair(UUID id) throws RepositoryException {
        try {
            RepairEnt repair = getRepairById(id);
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

    public List<RepairEnt> getClientRepairs(UUID clientId) throws RepositoryException {
        if (clientEntRepository.getClientById(clientId) == null) {
            throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
        }
        List<RepairEnt> repairs;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RepairEnt> criteriaQuery = criteriaBuilder.createQuery(RepairEnt.class);
        Root<RepairEnt> root = criteriaQuery.from(RepairEnt.class);
        Join<RepairEnt, ClientEnt> client = root.join(RepairEnt_.CLIENT_ENT);
        criteriaQuery.where(criteriaBuilder.equal(client.get(ClientEnt_.ID), clientId));
        TypedQuery<RepairEnt> query = entityManager.createQuery(criteriaQuery);
        repairs = query.getResultList();
        return repairs;
    }

    public List<RepairEnt> getAllRepairs() {
        List<RepairEnt> repairs;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RepairEnt> criteriaQuery = criteriaBuilder.createQuery(RepairEnt.class);
        Root<RepairEnt> root = criteriaQuery.from(RepairEnt.class);
        criteriaQuery.select(root);
        TypedQuery<RepairEnt> query = entityManager.createQuery(criteriaQuery);
        repairs = query.getResultList();
        return repairs;
    }

    public List<RepairEnt> getRepairs(boolean condition) {
        List<RepairEnt> repairs;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<RepairEnt> criteriaQuery = criteriaBuilder.createQuery(RepairEnt.class);
        Root<RepairEnt> root = criteriaQuery.from(RepairEnt.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(RepairEnt_.ARCHIVE), condition));
        TypedQuery<RepairEnt> query = entityManager.createQuery(criteriaQuery);
        repairs = query.getResultList();
        return repairs;
    }

    public RepairEnt repair(UUID id) throws RepositoryException, HardwareException {
        RepairEnt repair = getRepairById(id);
        if (repair == null || repair.getClientEnt().isArchive() || repair.getHardwareEnt().isArchive()) {
            throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
        }
        repair.getHardwareEnt().setArchive(true);
        repair.getDateRangeEnt().setEndDate(new Date());
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
