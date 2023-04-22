package org.pl.repair.module.adapter.data.aggregates;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.repair.module.adapter.data.converters.HardwareConverter;
import org.pl.repair.module.adapter.data.model.exceptions.RepositoryEntException;
import org.pl.repair.module.adapter.data.model.HardwareEnt;
import org.pl.repair.module.adapter.data.repositories.HardwareEntRepository;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.repair.module.model.Hardware;
import org.pl.gateway.module.infrastructure.hardware.ReadHardwarePort;
import org.pl.gateway.module.infrastructure.hardware.WriteHardwarePort;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class HardwareRepositoryAdapter implements ReadHardwarePort, WriteHardwarePort {
    @Inject
    private HardwareEntRepository hardwareEntRepository;
    @Inject
    private HardwareConverter hardwareConverter;

    @Override
    public Hardware createHardware(Hardware hardware) throws RepositoryException {
        try {
            return hardwareConverter.convert(hardwareEntRepository.saveHardware(hardwareConverter.convert(hardware)));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void deleteHardware(UUID uuid) throws RepositoryException {
        try {
            hardwareEntRepository.deleteHardware(uuid);
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<Hardware> getAllHardwares() {
        List<Hardware> hardwareList = new ArrayList<>();
        for (HardwareEnt hardware : hardwareEntRepository.getAllHardwares()) {
            hardwareList.add(hardwareConverter.convert(hardware));
        }
        return hardwareList;
    }

    @Override
    public List<Hardware> getAllPresentHardwareFilter(String substr) {
        List<Hardware> hardwareList = new ArrayList<>();
        for (HardwareEnt hardware : hardwareEntRepository.getAllPresentHardwareFilter(substr)) {
            hardwareList.add(hardwareConverter.convert(hardware));
        }
        return hardwareList;
    }

    @Override
    public List<Hardware> getAllPresentHardwares() {
        List<Hardware> hardwareList = new ArrayList<>();
        for (HardwareEnt hardware : hardwareEntRepository.getAllPresentHardwares()) {
            hardwareList.add(hardwareConverter.convert(hardware));
        }
        return hardwareList;
    }

    @Override
    public List<Hardware> getHardwareList(boolean condition) {
        List<Hardware> hardwareList = new ArrayList<>();
        for (HardwareEnt hardware : hardwareEntRepository.getHardwareList(condition)) {
            hardwareList.add(hardwareConverter.convert(hardware));
        }
        return hardwareList;
    }

    @Override
    public Hardware getHardware(UUID uuid) throws RepositoryException {
        try {
            return hardwareConverter.convert(hardwareEntRepository.getHardwareById(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Hardware updateHardware(UUID uuid, Hardware hardware) throws RepositoryException {
        try {
            return hardwareConverter.convert(hardwareEntRepository.updateHardware(uuid, hardwareConverter.convert(hardware)));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
