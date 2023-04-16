package org.pl.repair.module.infrastructure.hardware;

import org.pl.repair.module.model.Hardware;
import org.pl.repair.module.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteHardwarePort {
    Hardware createHardware(Hardware hardware) throws RepositoryException;
    void deleteHardware(UUID uuid) throws RepositoryException;
    Hardware updateHardware(UUID uuid, Hardware hardware) throws RepositoryException;
}
