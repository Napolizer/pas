package org.pl.adapter.data.repositories;

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
import org.pl.adapter.data.exceptions.RepositoryEntException;
import org.pl.adapter.data.model.HardwareEnt;
import org.pl.adapter.data.model.HardwareEnt_;

import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class HardwareEntRepository {
    @PersistenceContext
    private EntityManager entityManager;

    @Resource
    UserTransaction userTransaction;

    public HardwareEnt saveHardware(HardwareEnt hardware) throws RepositoryEntException {
        hardware.setId(UUID.randomUUID());
        hardware.getHardwareTypeEnt().setId(UUID.randomUUID());
        try {
            if (!entityManager.contains(hardware)) {
                userTransaction.begin();
                entityManager.persist(hardware);
                userTransaction.commit();
                return hardware;
            }
        } catch (Exception e) {
            throw new RepositoryEntException(e.getMessage());
        }
        throw new RepositoryEntException(RepositoryEntException.REPOSITORY_ADD_EXCEPTION);
    }

    public HardwareEnt getHardwareById(UUID uuid) throws RepositoryEntException {
        try {
            HardwareEnt hardware = entityManager.find(HardwareEnt.class, uuid);
            if (hardware != null && !hardware.isArchive()) {
                return hardware;
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryEntException(RepositoryEntException.REPOSITORY_GET_EXCEPTION);
        }
        return null;
    }

    public HardwareEnt updateHardware(UUID id, HardwareEnt hardware) throws RepositoryEntException {
        try {
            HardwareEnt hardwareToChange = entityManager.find(HardwareEnt.class, id);
            hardware.setId(hardwareToChange.getId());
            if (hardware.getHardwareTypeEnt() == null) {
                hardware.setHardwareTypeEnt(hardwareToChange.getHardwareTypeEnt());
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
            throw new RepositoryEntException(RepositoryEntException.REPOSITORY_GET_EXCEPTION);
        } catch (Exception e) {
            throw new RepositoryEntException(e.getMessage());
        }
    }

    public void deleteHardware(UUID id) throws RepositoryEntException {
        try {
            HardwareEnt hardware = entityManager.find(HardwareEnt.class, id);
            if (hardware == null) {
                throw new RepositoryEntException(RepositoryEntException.REPOSITORY_GET_EXCEPTION);
            }
            if (!hardware.isArchive()) {
                userTransaction.begin();
                hardware.setArchive(true);
                entityManager.merge(hardware);
                userTransaction.commit();
            } else {
                throw new RepositoryEntException(RepositoryEntException.REPOSITORY_ARCHIVE_EXCEPTION);
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryEntException(RepositoryEntException.REPOSITORY_GET_EXCEPTION);
        } catch (Exception e) {
            throw new RepositoryEntException(e.getMessage());
        }
    }

    public List<HardwareEnt> getHardwareList(boolean condition) {
        List<HardwareEnt> hardwareList;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<HardwareEnt> criteriaQuery = criteriaBuilder.createQuery(HardwareEnt.class);
        Root<HardwareEnt> root = criteriaQuery.from(HardwareEnt.class);
        criteriaQuery.where(criteriaBuilder.equal(root.get(HardwareEnt_.ARCHIVE), condition));
        TypedQuery<HardwareEnt> query = entityManager.createQuery(criteriaQuery);
        hardwareList = query.getResultList();
        return hardwareList;
    }

    public List<HardwareEnt> getAllHardwares() {
        List<HardwareEnt> hardwares;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<HardwareEnt> criteriaQuery = criteriaBuilder.createQuery(HardwareEnt.class);
        Root<HardwareEnt> root = criteriaQuery.from(HardwareEnt.class);
        criteriaQuery.select(root);
        TypedQuery<HardwareEnt> query = entityManager.createQuery(criteriaQuery);
        hardwares = query.getResultList();
        return hardwares;
    }

    public List<HardwareEnt> getAllPresentHardwares() {
        List<HardwareEnt> hardwares;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<HardwareEnt> criteriaQuery = criteriaBuilder.createQuery(HardwareEnt.class);
        Root<HardwareEnt> root = criteriaQuery.from(HardwareEnt.class);
        criteriaQuery.where(criteriaBuilder.isFalse(root.get(HardwareEnt_.archive)));
        TypedQuery<HardwareEnt> query = entityManager.createQuery(criteriaQuery);
        hardwares = query.getResultList();
        return hardwares;
    }

    public List<HardwareEnt> getAllPresentHardwareFilter(String substr) {
        List<HardwareEnt> hardwares;
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<HardwareEnt> criteriaQuery = criteriaBuilder.createQuery(HardwareEnt.class);
        Root<HardwareEnt> root = criteriaQuery.from(HardwareEnt.class);
        criteriaQuery
                .where(criteriaBuilder.isFalse(root.get(HardwareEnt_.archive)),
                        criteriaBuilder.like(root.get(HardwareEnt_.ID), "%" + substr + "%"));
        TypedQuery<HardwareEnt> query = entityManager.createQuery(criteriaQuery);
        hardwares = query.getResultList();
        return hardwares;
    }
}
