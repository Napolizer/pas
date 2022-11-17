package org.pl.repositories;

import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Hardware;

import java.util.ArrayList;
import java.util.UUID;

@AllArgsConstructor
public class HardwareRepository {
    private EntityManager entityManager;

    public Hardware saveHardware(Hardware hardware) throws RepositoryException {
        if (!entityManager.contains(hardware)) {
            entityManager.persist(hardware);
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

    public void deleteHardware(UUID id) throws RepositoryException {
        try {
            Hardware hardware = entityManager.find(Hardware.class, id);
            if (!hardware.isArchive()) {
                entityManager.merge(hardware);
                hardware.setArchive(true);
            } else {
                throw new RepositoryException(RepositoryException.REPOSITORY_ARCHIVE_EXCEPTION);
            }
        } catch (IllegalArgumentException ex) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
    }
}
