package org.pl.adapter.data.aggregates;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.pl.adapter.data.converters.RepairConverter;
import org.pl.adapter.data.model.exceptions.HardwareEntException;
import org.pl.adapter.data.model.exceptions.RepositoryEntException;
import org.pl.adapter.data.model.RepairEnt;
import org.pl.adapter.data.repositories.RepairEntRepository;
import org.pl.model.exceptions.HardwareException;
import org.pl.model.exceptions.RepositoryException;
import org.pl.infrastructure.repair.*;
import org.pl.model.Repair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ApplicationScoped
public class RepairRepositoryAdapter implements ReadRepairPort, WriteRepairPort {
    @Inject
    private RepairEntRepository repairEntRepository;

    @Inject
    private RepairConverter repairConverter;

    @Override
    public Repair createRepair(Repair repair) throws RepositoryException {
        try {
            return repairConverter.convert(repairEntRepository.saveRepair(repairConverter.convert(repair)));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Repair deleteRepair(UUID uuid) throws RepositoryException {
        try {
            return repairConverter.convert(repairEntRepository.deleteRepair(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<Repair> getAllRepairs() {
        List<Repair> repairList = new ArrayList<>();
        for (RepairEnt repair : repairEntRepository.getAllRepairs()) {
            repairList.add(repairConverter.convert(repair));
        }
        return repairList;
    }

    @Override
    public List<Repair> getClientRepairs(UUID clientId) throws RepositoryException {
        List<Repair> repairList = new ArrayList<>();
        try {
            for (RepairEnt repair : repairEntRepository.getClientRepairs(clientId)) {
                repairList.add(repairConverter.convert(repair));
            }
            return repairList;
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<Repair> getRepairList(boolean condition) {
        List<Repair> repairList = new ArrayList<>();
        for (RepairEnt repair : repairEntRepository.getRepairs(condition)) {
            repairList.add(repairConverter.convert(repair));
        }
        return repairList;
    }

    @Override
    public Repair getRepair(UUID uuid) throws RepositoryException {
        try {
            return repairConverter.convert(repairEntRepository.getRepairById(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Repair repair(UUID uuid) throws RepositoryException, HardwareException {
        try {
            return repairConverter.convert(repairEntRepository.repair(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        } catch (HardwareEntException e) {
            throw new HardwareException(e.getMessage());
        }
    }

    @Override
    public Repair updateRepair(UUID uuid, Repair repair) throws RepositoryException {
        try {
            return repairConverter.convert(repairEntRepository.updateRepair(uuid, repairConverter.convert(repair)));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
