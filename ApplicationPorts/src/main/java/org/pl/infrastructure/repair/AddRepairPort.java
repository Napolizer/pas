package org.pl.infrastructure.repair;

import org.pl.exceptions.RepositoryException;
import org.pl.model.Repair;

public interface AddRepairPort {
    Repair createRepair(Repair repair) throws RepositoryException;
}
