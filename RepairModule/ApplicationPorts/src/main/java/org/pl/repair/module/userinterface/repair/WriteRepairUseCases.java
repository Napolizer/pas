package org.pl.repair.module.userinterface.repair;

import org.pl.repair.module.model.Repair;
import org.pl.repair.module.model.exceptions.ClientException;
import org.pl.repair.module.model.exceptions.HardwareException;
import org.pl.repair.module.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteRepairUseCases {
    Repair add(Repair repair) throws RepositoryException;
    Repair archivize(UUID id) throws RepositoryException;
    Repair repair(UUID id) throws HardwareException, RepositoryException, ClientException;
    public Repair updateRepair(UUID id, Repair repair) throws RepositoryException;
}
