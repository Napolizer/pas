package org.pl.gateway.module.infrastructure.hardware;

import org.pl.gateway.module.model.Hardware;
import org.pl.gateway.module.model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface ReadHardwarePort {
    List<Hardware> getAllHardwares();
    List<Hardware> getAllPresentHardwareFilter(String substr);
    List<Hardware> getAllPresentHardwares();
    List<Hardware> getHardwareList(boolean condition);
    Hardware getHardware(UUID uuid) throws RepositoryException;
}
