package org.pl.gateway.module.infrastructure.repair;

import org.pl.gateway.module.model.Repair;
import org.pl.gateway.module.model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface ReadRepairPort {
    List<Repair> getAllRepairs();
    List<Repair> getClientRepairs(UUID clientId) throws RepositoryException;
    List<Repair> getRepairList(boolean condition);
    Repair getRepair(UUID uuid) throws RepositoryException;
}
