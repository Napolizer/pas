package org.pl.adapter.data.aggregates;

import jakarta.annotation.ManagedBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.adapter.data.model.exceptions.RepositoryEntException;
import org.pl.adapter.data.model.HardwareEnt;
import org.pl.adapter.data.repositories.HardwareEntRepository;
import org.pl.model.exceptions.RepositoryException;
import org.pl.infrastructure.hardware.*;
import org.pl.model.Hardware;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.beanutils.BeanUtils.copyProperties;

@ApplicationScoped
public class HardwareRepositoryAdapter implements
        AddHardwarePort, DeleteHardwarePort, GetAllHardwaresPort,
        GetAllPresentHardwareFilterPort, GetAllPresentHardwaresPort,
        GetHardwareListPort, GetHardwarePort, UpdateHardwarePort {
    @Inject
    private HardwareEntRepository hardwareEntRepository;

    private HardwareEnt convert(Hardware hardware) {
        try {
            HardwareEnt hardwareEnt = new HardwareEnt();
            copyProperties(hardwareEnt, hardware);
            return hardwareEnt;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Hardware convert(HardwareEnt hardwareEnt) {
        try {
            Hardware hardware = new Hardware();
            copyProperties(hardware, hardwareEnt);
            return hardware;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Hardware createHardware(Hardware hardware) throws RepositoryException {
        try {
            return convert(hardwareEntRepository.saveHardware(convert(hardware)));
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
            hardwareList.add(convert(hardware));
        }
        return hardwareList;
    }

    @Override
    public List<Hardware> getAllPresentHardwareFilter(String substr) {
        List<Hardware> hardwareList = new ArrayList<>();
        for (HardwareEnt hardware : hardwareEntRepository.getAllPresentHardwareFilter(substr)) {
            hardwareList.add(convert(hardware));
        }
        return hardwareList;
    }

    @Override
    public List<Hardware> getAllPresentHardwares() {
        List<Hardware> hardwareList = new ArrayList<>();
        for (HardwareEnt hardware : hardwareEntRepository.getAllPresentHardwares()) {
            hardwareList.add(convert(hardware));
        }
        return hardwareList;
    }

    @Override
    public List<Hardware> getHardwareList(boolean condition) {
        List<Hardware> hardwareList = new ArrayList<>();
        for (HardwareEnt hardware : hardwareEntRepository.getHardwareList(condition)) {
            hardwareList.add(convert(hardware));
        }
        return hardwareList;
    }

    @Override
    public Hardware getHardware(UUID uuid) throws RepositoryException {
        try {
            return convert(hardwareEntRepository.getHardwareById(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Hardware updateHardware(UUID uuid, Hardware hardware) throws RepositoryException {
        try {
            return convert(hardwareEntRepository.updateHardware(uuid, convert(hardware)));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
