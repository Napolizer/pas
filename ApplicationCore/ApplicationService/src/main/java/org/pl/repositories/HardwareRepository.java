package org.pl.repositories;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.UserTransaction;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Hardware;
import org.pl.model.Hardware_;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class HardwareRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserTransaction userTransaction;

    public Hardware saveHardware(Hardware hardware) throws RepositoryException {
        hardware.setId(UUID.randomUUID());
        hardware.getHardwareType().setId(UUID.randomUUID());
        try {
            if (!entityManager.contains(hardware)) {
                userTransaction.begin();
                entityManager.persist(hardware);
                userTransaction.commit();
                return hardware;
            }
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ADD_EXCEPTION);
    }

    public Hardware getHardwareById(UUID uuid) throws RepositoryException {
        try {
            Hardware hardware = entityManager.find(Hardware.class, uuid);
            if (hardware != null && !hardware.isArchive()) {
                return hardware;
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
        return null;
    }

    public Hardware updateHardware(UUID id, Hardware hardware) throws RepositoryException {
        try {
            Hardware hardwareToChange = entityManager.find(Hardware.class, id);
            hardware.setId(hardwareToChange.getId());
            if (hardware.getHardwareType() == null) {
                hardware.setHardwareType(hardwareToChange.getHardwareType());
            } if (hardware.getPrice() == null) {
                hardware.setPrice(hardwareToChange.getPrice());
            } if (hardware.getArchive() == null) {
                hardware.setArchive(hardwareToChange.getArchive());
            }
            userTransaction.begin();
            entityManager.merge(hardware);
            userTransaction.commit();
            return hardware;
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public void deleteHardware(UUID id) throws RepositoryException {
        try {
            Hardware hardware = entityManager.find(Hardware.class, id);
            if (hardware == null) {
                throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
            }
            if (!hardware.isArchive()) {
                userTransaction.begin();
                hardware.setArchive(true);
                entityManager.merge(hardware);
                userTransaction.commit();
            } else {
                throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        } catch (Exception e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public List<Hardware> getHardwareList(boolean condition) {
        List<Hardware> hardwareList;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hardware> criteriaQuery = criteriaBuilder.createQuery(Hardware.class);
        Root<Hardware> root = criteriaQuery.from(Hardware.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(Hardware_.ARCHIVE), condition));
        TypedQuery<Hardware> query = entityManager.createQuery(criteriaQuery);
        hardwareList = query.getResultList();
        return hardwareList;
    }

    public List<Hardware> getAllHardwares() {
        List<Hardware> hardwares;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hardware> criteriaQuery = criteriaBuilder.createQuery(Hardware.class);
        Root<Hardware> root = criteriaQuery.from(Hardware.class);
        criteriaQuery.select(root);
        TypedQuery<Hardware> query = entityManager.createQuery(criteriaQuery);
        hardwares = query.getResultList();
        return hardwares;
    }

    public List<Hardware> getAllPresentHardwares() {
        List<Hardware> hardwares;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hardware> criteriaQuery = criteriaBuilder.createQuery(Hardware.class);
        Root<Hardware> root = criteriaQuery.from(Hardware.class);
        criteriaQuery.where(criteriaBuilder.isFalse(root.get(Hardware_.archive)));
        TypedQuery<Hardware> query = entityManager.createQuery(criteriaQuery);
        hardwares = query.getResultList();
        return hardwares;
    }

    public List<Hardware> getAllPresentHardwareFilter(String substr) {
        List<Hardware> hardwares;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hardware> criteriaQuery = criteriaBuilder.createQuery(Hardware.class);
        Root<Hardware> root = criteriaQuery.from(Hardware.class);
        criteriaQuery
                .where(criteriaBuilder.isFalse(root.get(Hardware_.archive)),
                        criteriaBuilder.like(root.get(Hardware_.ID), "%" + substr + "%"));
        TypedQuery<Hardware> query = entityManager.createQuery(criteriaQuery);
        hardwares = query.getResultList();
        return hardwares;
    }
}