package org.pl.gateway.module.userinterface.hardware;

import org.pl.repair.module.model.Hardware;
import org.pl.repair.module.model.exceptions.HardwareException;
import org.pl.repair.module.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteHardwareUseCases {
    Hardware add(Hardware hardware) throws RepositoryException, HardwareException;
    void archive(UUID id) throws RepositoryException;
    Hardware updateHardware(UUID id, Hardware hardware) throws RepositoryException;

}
