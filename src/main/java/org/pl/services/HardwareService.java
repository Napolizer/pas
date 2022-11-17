package org.pl.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.exceptions.ServiceException;
import org.pl.model.*;
import org.pl.repositories.HardwareRepository;

import java.util.UUID;

@ApplicationScoped
public class HardwareService {
    @Inject
    private HardwareRepository hardwareRepository;

    public Hardware create(Hardware hardware) throws RepositoryException {
        return hardwareRepository.create(hardware);
    }

    public Hardware update(Hardware hardware) throws RepositoryException {
        return hardwareRepository.update(hardware);
    }

    public boolean isHardwareArchive(UUID id) throws RepositoryException {
        return hardwareRepository.read(id).isArchive();
    }

    public Hardware get(UUID id) throws RepositoryException {
        return hardwareRepository.read(id);
    }

    public void archivize(UUID id) throws RepositoryException {
        hardwareRepository.delete(id);
    }
}
