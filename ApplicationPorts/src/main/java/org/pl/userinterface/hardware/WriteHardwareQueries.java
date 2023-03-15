package org.pl.userinterface.hardware;

import org.pl.model.Hardware;
import org.pl.model.exceptions.HardwareException;
import org.pl.model.exceptions.RepositoryException;

import java.util.UUID;

public interface WriteHardwareQueries {
    Hardware add(Hardware hardware) throws RepositoryException, HardwareException;
    void archive(UUID id) throws RepositoryException;
    Hardware updateHardware(UUID id, Hardware hardware) throws RepositoryException;

}
