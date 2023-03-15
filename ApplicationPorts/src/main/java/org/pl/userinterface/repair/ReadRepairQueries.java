package org.pl.userinterface.repair;


import org.pl.model.Repair;
import org.pl.model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface ReadRepairQueries {
    Repair get(UUID id) throws RepositoryException;
    String getInfo(UUID id) throws RepositoryException;
    List<Repair> getAllClientRepairs(UUID clientId) throws RepositoryException;
    boolean isRepairArchive(UUID id) throws RepositoryException;
    int getPresentSize();
    int getArchiveSize();
    List<Repair> getClientsPastRepairs(UUID id) throws RepositoryException;
    List<Repair> getClientsPresentRepairs(UUID id) throws RepositoryException;
    List<Repair> getAllRepairs();
}
