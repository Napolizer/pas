package org.pl.userinterface.repair;

import org.pl.model.Repair;
import org.pl.model.exceptions.ClientException;
import org.pl.model.exceptions.HardwareException;
import org.pl.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteRepairUseCases {
    Repair add(Repair repair) throws RepositoryException;
    Repair archivize(UUID id) throws RepositoryException;
    Repair repair(UUID id) throws HardwareException, RepositoryException, ClientException;
    public Repair updateRepair(UUID id, Repair repair) throws RepositoryException;
}
