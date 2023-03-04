package org.pl.adapter.data.aggregates;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.adapter.data.converters.HardwareConverter;
import org.pl.adapter.data.model.exceptions.RepositoryEntException;
import org.pl.adapter.data.model.HardwareEnt;
import org.pl.adapter.data.repositories.HardwareEntRepository;
import org.pl.model.exceptions.RepositoryException;
import org.pl.infrastructure.hardware.*;
import org.pl.model.Hardware;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class HardwareRepositoryAdapter implements
        AddHardwarePort, DeleteHardwarePort, GetAllHardwaresPort,
        GetAllPresentHardwareFilterPort, GetAllPresentHardwaresPort,
        GetHardwareListPort, GetHardwarePort, UpdateHardwarePort {
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
