package org.pl.infrastructure.repair;

import org.pl.model.Repair;
import org.pl.model.exceptions.HardwareException;
import org.pl.model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface WriteRepairPort {
    Repair createRepair(Repair repair) throws RepositoryException;
    Repair deleteRepair(UUID uuid) throws RepositoryException;
    Repair repair(UUID uuid) throws RepositoryException, HardwareException;
    Repair updateRepair(UUID uuid, Repair repair) throws RepositoryException;
}
