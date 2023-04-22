package org.pl.repair.module.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.repair.module.model.exceptions.ClientException;
import org.pl.repair.module.model.exceptions.HardwareException;
import org.pl.repair.module.model.exceptions.RepositoryException;
import org.pl.repair.module.model.Repair;
import org.pl.gateway.module.infrastructure.repair.ReadRepairPort;
import org.pl.gateway.module.infrastructure.repair.WriteRepairPort;
import org.pl.gateway.module.userinterface.repair.ReadRepairUseCases;
import org.pl.gateway.module.userinterface.repair.WriteRepairUseCases;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class RepairService implements ReadRepairUseCases, WriteRepairUseCases {
    @Inject
    private ReadRepairPort readRepairPort;

    @Inject
    private WriteRepairPort writeRepairPort;
    public Repair add(Repair repair) throws RepositoryException {
        return writeRepairPort.createRepair(repair);
    }

    public Repair get(UUID id) throws RepositoryException {
        return readRepairPort.getRepair(id);
    }

    public String getInfo(UUID id) throws RepositoryException {
        return readRepairPort.getRepair(id).toString();
    }

    public List<Repair> getAllClientRepairs(UUID clientId) throws RepositoryException {
        return readRepairPort.getClientRepairs(clientId);
    }

    public boolean isRepairArchive(UUID id) throws RepositoryException {
        return readRepairPort.getRepair(id).getArchive();
    }
    public Repair archivize(UUID id) throws RepositoryException {
        return writeRepairPort.deleteRepair(id);
    }

    public Repair repair(UUID id) throws HardwareException, RepositoryException, ClientException {
        return writeRepairPort.repair(id);
    }

    public int getPresentSize() {
        return readRepairPort.getRepairList(false).size();
    }

    public int getArchiveSize() {
        return readRepairPort.getRepairList(true).size();
    }

    public List<Repair> getClientsPastRepairs(UUID uuid) throws RepositoryException {
        List<Repair> pastRepairs = new ArrayList<>();
        List<Repair> repairs = readRepairPort.getClientRepairs(uuid);
        for (Repair repair : repairs) {
            if (repair.getArchive()) {
                pastRepairs.add(repair);
            }
        }
        return pastRepairs;
    }

    public List<Repair> getClientsPresentRepairs(UUID uuid) throws RepositoryException {
        List<Repair> presentRepairs = new ArrayList<>();
        List<Repair> repairs = readRepairPort.getClientRepairs(uuid);
        for (Repair repair : repairs) {
            if (!repair.getArchive()) {
                presentRepairs.add(repair);
            }
        }
        return presentRepairs;
    }

    public List<Repair> getAllRepairs() {
        return readRepairPort.getAllRepairs();
    }

    public Repair updateRepair(UUID uuid, Repair repair) throws RepositoryException {
        return writeRepairPort.updateRepair(uuid, repair);
    }
}
