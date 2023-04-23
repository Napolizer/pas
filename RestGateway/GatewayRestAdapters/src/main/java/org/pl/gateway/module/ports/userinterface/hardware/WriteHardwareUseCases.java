package org.pl.gateway.module.ports.userinterface.hardware;



import org.pl.gateway.module.model.HardwareRest;
import org.pl.gateway.module.model.exceptions.HardwareRestException;
import org.pl.gateway.module.model.exceptions.RepositoryRestException;

import java.util.UUID;

public interface WriteHardwareUseCases {
    HardwareRest add(HardwareRest hardware) throws RepositoryRestException, HardwareRestException;
    void archive(UUID id) throws RepositoryRestException;
    HardwareRest updateHardware(UUID id, HardwareRest hardware) throws RepositoryRestException;

}
