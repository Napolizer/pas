package org.pl.gateway.module.ports.userinterface.repair;



import org.pl.gateway.module.model.RepairRest;
import org.pl.gateway.module.model.exceptions.ClientRestException;
import org.pl.gateway.module.model.exceptions.HardwareRestException;
import org.pl.gateway.module.model.exceptions.RepositoryRestException;

import java.util.UUID;

public interface WriteRepairUseCases {
    RepairRest add(RepairRest repair) throws RepositoryRestException;
    RepairRest archivize(UUID id) throws RepositoryRestException;
    RepairRest repair(UUID id) throws HardwareRestException, RepositoryRestException, ClientRestException;
    public RepairRest updateRepair(UUID id, RepairRest repair) throws RepositoryRestException;
}
