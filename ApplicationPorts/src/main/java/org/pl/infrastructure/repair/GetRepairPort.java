package org.pl.infrastructure.repair;

import org.pl.exceptions.RepositoryException;
import org.pl.model.Repair;

import java.util.UUID;

public interface GetRepairPort {
    Repair getRepair(UUID uuid) throws RepositoryException;
}
