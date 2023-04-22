package org.pl.gateway.module.infrastructure.hardware;

import org.pl.repair.module.model.Hardware;
import org.pl.repair.module.model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface ReadHardwarePort {
    List<Hardware> getAllHardwares();
    List<Hardware> getAllPresentHardwareFilter(String substr);
    List<Hardware> getAllPresentHardwares();
    List<Hardware> getHardwareList(boolean condition);
    Hardware getHardware(UUID uuid) throws RepositoryException;
}
