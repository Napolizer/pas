package org.pl.infrastructure.hardware;

import org.pl.model.exceptions.RepositoryException;
import org.pl.model.Hardware;

import java.util.UUID;

public interface UpdateHardwarePort {
    Hardware updateHardware(UUID uuid, Hardware hardware) throws RepositoryException;
}
