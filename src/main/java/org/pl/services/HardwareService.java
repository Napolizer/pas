package org.pl.services;

import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;
import org.pl.repositories.HardwareRepository;

import java.util.Objects;
import java.util.UUID;

public class HardwareService {
    private final HardwareRepository hardwareRepository;

    public HardwareService(HardwareRepository hardwareRepository) {
        this.hardwareRepository = hardwareRepository;
    }

    public Hardware add(Hardware hardware) throws RepositoryException, HardwareException {
        if (hardware.getPrice() < 0)
            throw new HardwareException(HardwareException.HARDWARE_PRICE_EXCEPTION);
        if (Objects.equals(hardware.getHardwareType(), null))
            throw new HardwareException(HardwareException.HARDWARE_TYPE_EXCEPTION);
        return hardwareRepository.saveHardware(hardware);
    }

    public boolean isHardwareArchive(UUID id) throws RepositoryException {
        return hardwareRepository.getHardwareById(id).isArchive();
    }

    public Hardware get(UUID id) throws RepositoryException {
        return hardwareRepository.getHardwareById(id);
    }

    public String getInfo(UUID id) throws RepositoryException {
        return hardwareRepository.getHardwareById(id).toString();
    }

    public void archive(UUID id) throws RepositoryException {
        hardwareRepository.deleteHardware(id);
    }

    public int getPresentSize() {
        return hardwareRepository.getHardwareList(false).size();
    }

    public int getArchiveSize() {
        return hardwareRepository.getHardwareList(true).size();
    }
}
