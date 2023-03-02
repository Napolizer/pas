package org.pl.infrastructure.hardware;

import org.pl.exceptions.RepositoryException;

import java.util.UUID;

public interface DeleteHardwarePort {
    void deleteHardware(UUID uuid) throws RepositoryException;
}
