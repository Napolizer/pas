package org.pl.infrastructure.hardware;

import org.pl.model.Hardware;
import org.pl.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteHardwarePort {
    Hardware createHardware(Hardware hardware) throws RepositoryException;
    void deleteHardware(UUID uuid) throws RepositoryException;
    Hardware updateHardware(UUID uuid, Hardware hardware) throws RepositoryException;
}