package org.pl.gateway.module.userinterface.repair;


import org.pl.gateway.module.model.Repair;
import org.pl.gateway.module.model.exceptions.RepositoryException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.UUID;

public interface ReadRepairUseCases {
    Repair get(UUID id) throws RepositoryException, URISyntaxException, IOException, InterruptedException;
    String getInfo(UUID id) throws RepositoryException, URISyntaxException, IOException, InterruptedException;
    List<Repair> getAllClientRepairs(UUID clientId) throws RepositoryException, URISyntaxException, IOException, InterruptedException;
    boolean isRepairArchive(UUID id) throws RepositoryException, URISyntaxException, IOException, InterruptedException;
    int getPresentSize() throws URISyntaxException, IOException, InterruptedException;
    int getArchiveSize() throws URISyntaxException, IOException, InterruptedException;
    List<Repair> getClientsPastRepairs(UUID id) throws RepositoryException, URISyntaxException, IOException, InterruptedException;
    List<Repair> getClientsPresentRepairs(UUID id) throws RepositoryException, URISyntaxException, IOException, InterruptedException;
    List<Repair> getAllRepairs() throws URISyntaxException, IOException, InterruptedException;
}
