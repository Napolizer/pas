package org.pl.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.*;
import org.pl.repositories.HardwareRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

//@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class HardwareService {
    @Inject
    private HardwareRepository hardwareRepository;

    public Hardware add(Hardware hardware) throws RepositoryException, HardwareException {
        if (hardware.getPrice() < 0)
            throw new HardwareException(HardwareException.HARDWARE_PRICE_EXCEPTION);
        if (Objects.equals(hardware.getHardwareType(), null))
            throw new HardwareException(HardwareException.HARDWARE_TYPE_EXCEPTION);
        return hardwareRepository.saveHardware(hardware);
    }

    public boolean isHardwareArchive(UUID id) throws RepositoryException {
        Hardware hardware = hardwareRepository.getHardwareById(id);
        if (hardware == null) {
            return false;
        }
        return hardware.isArchive();
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

    public List<Hardware> getAllHardwares() {
        return hardwareRepository.getAllHardwares();
    }

    public Hardware updateHardware(UUID uuid, Hardware hardware) throws RepositoryException {
        return hardwareRepository.updateHardware(uuid, hardware);
    }

    public HardwareType getHardwareTypeById(UUID uuid) throws RepositoryException {
        List<Hardware> hardwares = hardwareRepository.getAllHardwares();
        for (Hardware hardware : hardwares) {
            if (hardware.getHardwareType().getId().equals(uuid)) {
                return hardware.getHardwareType();
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public List<Hardware> getAllPresentHardware() {
        return hardwareRepository.getAllPresentHardwares();
    }

    public List<Hardware> getAllPresentHardwareFilter(String substr) {
        return hardwareRepository.getAllPresentHardwareFilter(substr);
    }
}
