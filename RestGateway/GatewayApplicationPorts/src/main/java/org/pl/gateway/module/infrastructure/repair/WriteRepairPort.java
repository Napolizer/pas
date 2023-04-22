package org.pl.gateway.module.infrastructure.repair;

import org.pl.repair.module.model.Repair;
import org.pl.repair.module.model.exceptions.HardwareException;
import org.pl.repair.module.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteRepairPort {
    Repair createRepair(Repair repair) throws RepositoryException;
    Repair deleteRepair(UUID uuid) throws RepositoryException;
    Repair repair(UUID uuid) throws RepositoryException, HardwareException;
    Repair updateRepair(UUID uuid, Repair repair) throws RepositoryException;
}
