package org.pl.repair.module.userinterface.hardware;

import org.pl.repair.module.model.Hardware;
import org.pl.repair.module.model.HardwareType;
import org.pl.repair.module.model.exceptions.RepositoryException;

import java.util.List;
import java.util.UUID;

public interface ReadHardwareUseCases {
    boolean isHardwareArchive(UUID id) throws RepositoryException;
    Hardware get(UUID id) throws RepositoryException;
    String getInfo(UUID id) throws RepositoryException;
    int getPresentSize();
    int getArchiveSize();
    List<Hardware> getAllHardwares();
    HardwareType getHardwareTypeById(UUID id) throws RepositoryException;
    List<Hardware> getAllPresentHardware();
    List<Hardware> getAllPresentHardwareFilter(String substr);
}
