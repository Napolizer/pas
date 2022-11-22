package org.pl.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Hardware;
import org.pl.model.Hardware_;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@ApplicationScoped
public class HardwareRepository {
    private EntityManager entityManager;

    HardwareRepository() {
        var emf = Persistence.createEntityManagerFactory("POSTGRES_REPAIR_PU");
        entityManager = emf.createEntityManager();
    }

    public Hardware saveHardware(Hardware hardware) throws RepositoryException {
        if (!entityManager.contains(hardware)) {
            entityManager.getTransaction().begin();
            entityManager.persist(hardware);
            entityManager.getTransaction().commit();
            return hardware;
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_ADD_EXCEPTION);
    }

    public Hardware getHardwareById(UUID uuid) throws RepositoryException {
        try {
            Hardware hardware = entityManager.find(Hardware.class, uuid);
            if (hardware != null) {
                return hardware;
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
        return null;
    }

    public Hardware updateHardware(UUID id, @NotNull Hardware hardware) throws RepositoryException {
        try {
            Hardware hardwareToChange = entityManager.find(Hardware.class, id);
            hardwareToChange.setHardwareType(hardware.getHardwareType());
            hardwareToChange.setPrice(hardware.getPrice());
            hardwareToChange.setArchive(hardware.isArchive());
            hardwareToChange.getHardwareType().setCondition(hardware.getHardwareType().getCondition());
            entityManager.getTransaction().begin();
            entityManager.merge(hardwareToChange);
            entityManager.getTransaction().commit();
            return hardwareToChange;
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }

    public void deleteHardware(UUID id) throws RepositoryException {
        try {
            Hardware hardware = entityManager.find(Hardware.class, id);
            if (!hardware.isArchive()) {
                entityManager.getTransaction().begin();
                entityManager.merge(hardware);
                hardware.setArchive(true);
                entityManager.getTransaction().commit();
            } else {
                throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
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
}
