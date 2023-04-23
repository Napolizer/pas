package org.pl.gateway.module.ports.userinterface.repair;


import org.pl.gateway.module.model.RepairRest;
import org.pl.gateway.module.model.exceptions.RepositoryRestException;

import java.util.List;
import java.util.UUID;

public interface ReadRepairUseCases {
    RepairRest get(UUID id) throws RepositoryRestException;
    String getInfo(UUID id) throws RepositoryRestException;
    List<RepairRest> getAllClientRepairs(UUID clientId) throws RepositoryRestException;
    boolean isRepairArchive(UUID id) throws RepositoryRestException;
    int getPresentSize();
    int getArchiveSize();
    List<RepairRest> getClientsPastRepairs(UUID id) throws RepositoryRestException;
    List<RepairRest> getClientsPresentRepairs(UUID id) throws RepositoryRestException;
    List<RepairRest> getAllRepairs();
}
