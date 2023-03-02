package org.pl.infrastructure.hardware;

import org.pl.exceptions.RepositoryException;
import org.pl.model.Hardware;

import java.util.UUID;

public interface GetHardwarePort {
    Hardware getHardware(UUID uuid) throws RepositoryException;
}
