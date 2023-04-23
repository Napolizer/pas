package org.pl.gateway.module.ports.userinterface.hardware;


import org.pl.gateway.module.model.HardwareRest;
import org.pl.gateway.module.model.HardwareTypeRest;
import org.pl.gateway.module.model.exceptions.RepositoryRestException;

import java.util.List;
import java.util.UUID;

public interface ReadHardwareUseCases {
    Boolean isHardwareArchive(UUID id) throws RepositoryRestException;
    HardwareRest get(UUID id) throws RepositoryRestException;
    String getInfo(UUID id) throws RepositoryRestException;
    Integer getPresentSize();
    Integer getArchiveSize();
    List<HardwareRest> getAllHardwares();
    HardwareTypeRest getHardwareTypeById(UUID id) throws RepositoryRestException;
    List<HardwareRest> getAllPresentHardware();
    List<HardwareRest> getAllPresentHardwareFilter(String substr);
}
