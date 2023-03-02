package org.pl.infrastructure.repair;

import org.pl.model.exceptions.HardwareException;
import org.pl.model.exceptions.RepositoryException;
import org.pl.model.Repair;

import java.util.UUID;

public interface RepairPort {
    Repair repair(UUID uuid) throws RepositoryException, HardwareException;
}
