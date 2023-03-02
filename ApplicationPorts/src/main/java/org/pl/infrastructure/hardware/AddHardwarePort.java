package org.pl.infrastructure.hardware;

import org.pl.model.exceptions.RepositoryException;
import org.pl.model.Hardware;

public interface AddHardwarePort {
    Hardware createHardware(Hardware hardware) throws RepositoryException;
}
