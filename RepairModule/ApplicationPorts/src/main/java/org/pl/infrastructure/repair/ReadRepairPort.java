package org.pl.infrastructure.repair;

import org.pl.model.Repair;
import org.pl.model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface ReadRepairPort {
    List<Repair> getAllRepairs();
    List<Repair> getClientRepairs(UUID clientId) throws RepositoryException;
    List<Repair> getRepairList(boolean condition);
    Repair getRepair(UUID uuid) throws RepositoryException;
}
