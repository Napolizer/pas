package org.pl.gateway.module.infrastructure.hardware;

import org.pl.gateway.module.model.Hardware;
import org.pl.gateway.module.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteHardwarePort {
    Hardware createHardware(Hardware hardware) throws RepositoryException;
    void deleteHardware(UUID uuid) throws RepositoryException;
    Hardware updateHardware(UUID uuid, Hardware hardware) throws RepositoryException;
}
