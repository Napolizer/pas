package org.pl.infrastructure.repair;

import org.pl.exceptions.RepositoryException;
import org.pl.model.Repair;

import java.util.UUID;

public interface UpdateRepairPort {
    Repair updateRepair(UUID uuid, Repair repair) throws RepositoryException;
}
