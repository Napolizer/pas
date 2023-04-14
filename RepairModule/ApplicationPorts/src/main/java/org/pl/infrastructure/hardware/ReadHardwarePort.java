package org.pl.infrastructure.hardware;

import org.pl.model.Hardware;
import org.pl.model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface ReadHardwarePort {
    List<Hardware> getAllHardwares();
    List<Hardware> getAllPresentHardwareFilter(String substr);
    List<Hardware> getAllPresentHardwares();
    List<Hardware> getHardwareList(boolean condition);
    Hardware getHardware(UUID uuid) throws RepositoryException;
}
