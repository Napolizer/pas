package org.pl.infrastructure.repair;

import org.pl.model.exceptions.RepositoryException;
import org.pl.model.Repair;

import java.util.List;
import java.util.UUID;

public interface GetClientRepairsPort {
    List<Repair> getClientRepairs(UUID clientId) throws RepositoryException;
}
