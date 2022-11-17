package org.pl.repositories;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Hardware;

import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class HardwareRepository {
    private EntityManager entityManager;

    public Hardware saveHardware(Hardware hardware) {
        if (!entityManager.contains(hardware)) {
            entityManager.persist(hardware);
        } else {
            entityManager.merge(hardware);
        }
        return hardware;
    }

    public Hardware getHardwareById(UUID uuid) throws RepositoryException {
        Hardware hardware = entityManager.find(Hardware.class, uuid);
        if (hardware != null) {
            return hardware;
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public Hardware deleteHardware(UUID id) throws RepositoryException {
        entityManager.getTransaction().begin();
        Hardware hardware = entityManager.find(Hardware.class, id);
        if (hardware != null) {
            if (!hardware.isArchive()) {
                entityManager.merge(hardware);
                hardware.setArchive(true);
                entityManager.getTransaction().commit();
                return hardware;
            }
            throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }
}
