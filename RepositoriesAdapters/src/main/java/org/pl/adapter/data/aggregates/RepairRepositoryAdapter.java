package org.pl.adapter.data.aggregates;

import jakarta.inject.Inject;
import org.pl.adapter.data.exceptions.HardwareEntException;
import org.pl.adapter.data.exceptions.RepositoryEntException;
import org.pl.adapter.data.model.RepairEnt;
import org.pl.adapter.data.repositories.RepairEntRepository;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepositoryException;
import org.pl.infrastructure.repair.*;
import org.pl.model.Repair;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.beanutils.BeanUtils.copyProperties;

public class RepairRepositoryAdapter implements
        AddRepairPort, DeleteRepairPort, GetAllRepairsPort,
        GetClientRepairsPort, GetRepairListPort, GetRepairPort,
        RepairPort, UpdateRepairPort {
    @Inject
    private RepairEntRepository repairEntRepository;

    private RepairEnt convert(Repair repair) {
        try {
            RepairEnt repairEnt = new RepairEnt();
            copyProperties(repairEnt, repair);
            return repairEnt;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Repair convert(RepairEnt repairEnt) {
        try {
            Repair repair = new Repair();
            copyProperties(repair, repairEnt);
            return repair;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Repair createRepair(Repair repair) throws RepositoryException {
        try {
            return convert(repairEntRepository.saveRepair(convert(repair)));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Repair deleteRepair(UUID uuid) throws RepositoryException {
        try {
            return convert(repairEntRepository.deleteRepair(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public List<Repair> getAllRepairs() {
        List<Repair> repairList = new ArrayList<>();
        for (RepairEnt repair : repairEntRepository.getAllRepairs()) {
            repairList.add(convert(repair));
        }
        return repairList;
    }

    @Override
    public List<Repair> getClientRepairs(UUID clientId) throws RepositoryException {
        List<Repair> repairList = new ArrayList<>();
        try {
            for (RepairEnt repair : repairEntRepository.getClientRepairs(clientId)) {
                repairList.add(convert(repair));
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
            repairList.add(convert(repair));
        }
        return repairList;
    }

    @Override
    public Repair getRepair(UUID uuid) throws RepositoryException {
        try {
            return convert(repairEntRepository.getRepairById(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public Repair repair(UUID uuid) throws RepositoryException, HardwareException {
        try {
            return convert(repairEntRepository.repair(uuid));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        } catch (HardwareEntException e) {
            throw new HardwareException(e.getMessage());
        }
    }

    @Override
    public Repair updateRepair(UUID uuid, Repair repair) throws RepositoryException {
        try {
            return convert(repairEntRepository.updateRepair(uuid, convert(repair)));
        } catch (RepositoryEntException e) {
            throw new RepositoryException(e.getMessage());
        }
    }
}
