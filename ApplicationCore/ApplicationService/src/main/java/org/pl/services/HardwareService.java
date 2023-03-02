package org.pl.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pl.model.exceptions.HardwareException;
import org.pl.model.exceptions.RepositoryException;
import org.pl.infrastructure.hardware.*;
import org.pl.model.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

//@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class HardwareService {
    @Inject
    private AddHardwarePort addHardwarePort;
    @Inject
    private DeleteHardwarePort deleteHardwarePort;
    @Inject
    private GetAllHardwaresPort getAllHardwaresPort;
    @Inject
    private GetAllPresentHardwareFilterPort getAllPresentHardwareFilterPort;
    @Inject
    private GetAllPresentHardwaresPort getAllPresentHardwaresPort;
    @Inject
    private GetHardwareListPort getHardwareListPort;
    @Inject
    private GetHardwarePort getHardwarePort;
    @Inject
    private UpdateHardwarePort updateHardwarePort;

    public Hardware add(Hardware hardware) throws RepositoryException, HardwareException {
        if (hardware.getPrice() < 0)
            throw new HardwareException(HardwareException.HARDWARE_PRICE_EXCEPTION);
        if (Objects.equals(hardware.getHardwareType(), null))
            throw new HardwareException(HardwareException.HARDWARE_TYPE_EXCEPTION);
        return addHardwarePort.createHardware(hardware);
    }

    public boolean isHardwareArchive(UUID id) throws RepositoryException {
        Hardware hardware = getHardwarePort.getHardware(id);
        if (hardware == null) {
            return false;
        }
        return hardware.isArchive();
    }

    public Hardware get(UUID id) throws RepositoryException {
        return getHardwarePort.getHardware(id);
    }

    public String getInfo(UUID id) throws RepositoryException {
        return getHardwarePort.getHardware(id).toString();
    }

    public void archive(UUID id) throws RepositoryException {
        deleteHardwarePort.deleteHardware(id);
    }

    public int getPresentSize() {
        return getHardwareListPort.getHardwareList(false).size();
    }

    public int getArchiveSize() {
        return getHardwareListPort.getHardwareList(true).size();
    }

    public List<Hardware> getAllHardwares() {
        return getAllHardwaresPort.getAllHardwares();
    }

    public Hardware updateHardware(UUID uuid, Hardware hardware) throws RepositoryException {
        return updateHardwarePort.updateHardware(uuid, hardware);
    }

    public HardwareType getHardwareTypeById(UUID uuid) throws RepositoryException {
        List<Hardware> hardwares = getAllHardwaresPort.getAllHardwares();
        for (Hardware hardware : hardwares) {
            if (hardware.getHardwareType().getId().equals(uuid)) {
                return hardware.getHardwareType();
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public List<Hardware> getAllPresentHardware() {
        return getAllPresentHardwaresPort.getAllPresentHardwares();
    }

    public List<Hardware> getAllPresentHardwareFilter(String substr) {
        return getAllPresentHardwareFilterPort.getAllPresentHardwareFilter(substr);
    }
}
