package org.pl.repair.module.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.pl.repair.module.model.Hardware;
import org.pl.repair.module.model.HardwareType;
import org.pl.repair.module.model.exceptions.HardwareException;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.gateway.module.infrastructure.hardware.ReadHardwarePort;
import org.pl.gateway.module.infrastructure.hardware.WriteHardwarePort;
import org.pl.gateway.module.userinterface.hardware.ReadHardwareUseCases;
import org.pl.gateway.module.userinterface.hardware.WriteHardwareUseCases;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

//@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class HardwareService implements ReadHardwareUseCases, WriteHardwareUseCases {
    @Inject
    private ReadHardwarePort readHardwarePort;

    @Inject
    private WriteHardwarePort writeHardwarePort;

    public Hardware add(Hardware hardware) throws RepositoryException, HardwareException {
        if (hardware.getPrice() < 0)
            throw new HardwareException(HardwareException.HARDWARE_PRICE_EXCEPTION);
        if (Objects.equals(hardware.getHardwareType(), null))
            throw new HardwareException(HardwareException.HARDWARE_TYPE_EXCEPTION);
        return writeHardwarePort.createHardware(hardware);
    }

    public boolean isHardwareArchive(UUID id) throws RepositoryException {
        Hardware hardware = readHardwarePort.getHardware(id);
        if (hardware == null) {
            throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
        }
        return hardware.getArchive();
    }

    public Hardware get(UUID id) throws RepositoryException {
        return readHardwarePort.getHardware(id);
    }

    public String getInfo(UUID id) throws RepositoryException {
        return readHardwarePort.getHardware(id).toString();
    }

    public void archive(UUID id) throws RepositoryException {
        writeHardwarePort.deleteHardware(id);
    }

    public int getPresentSize() {
        return readHardwarePort.getHardwareList(false).size();
    }

    public int getArchiveSize() {
        return readHardwarePort.getHardwareList(true).size();
    }

    public List<Hardware> getAllHardwares() {
        return readHardwarePort.getAllHardwares();
    }

    public Hardware updateHardware(UUID uuid, Hardware hardware) throws RepositoryException {
        return writeHardwarePort.updateHardware(uuid, hardware);
    }

    public HardwareType getHardwareTypeById(UUID uuid) throws RepositoryException {
        List<Hardware> hardwares = readHardwarePort.getAllHardwares();
        for (Hardware hardware : hardwares) {
            if (hardware.getHardwareType().getId().equals(uuid)) {
                return hardware.getHardwareType();
            }
        }
        throw new RepositoryException(RepositoryException.REPOSITORY_GET_EXCEPTION);
    }

    public List<Hardware> getAllPresentHardware() {
        return readHardwarePort.getAllPresentHardwares();
    }

    public List<Hardware> getAllPresentHardwareFilter(String substr) {
        return readHardwarePort.getAllPresentHardwareFilter(substr);
    }
}
