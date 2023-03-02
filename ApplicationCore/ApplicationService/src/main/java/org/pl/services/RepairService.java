package org.pl.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.model.exceptions.ClientException;
import org.pl.model.exceptions.HardwareException;
import org.pl.model.exceptions.RepositoryException;
import org.pl.infrastructure.repair.*;
import org.pl.model.Repair;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class RepairService {
    @Inject
    private AddRepairPort addRepairPort;
    @Inject
    private DeleteRepairPort deleteRepairPort;
    @Inject
    private GetAllRepairsPort getAllRepairsPort;
    @Inject
    private GetClientRepairsPort getClientRepairsPort;
    @Inject
    private GetRepairListPort getRepairListPort;
    @Inject
    private GetRepairPort getRepairPort;
    @Inject
    private RepairPort repairPort;
    @Inject
    private UpdateRepairPort updateRepairPort;

    public Repair add(Repair repair) throws RepositoryException {
        return addRepairPort.createRepair(repair);
    }

    public Repair get(UUID id) throws RepositoryException {
        return getRepairPort.getRepair(id);
    }

    public String getInfo(UUID id) throws RepositoryException {
        return getRepairPort.getRepair(id).toString();
    }

    public List<Repair> getAllClientRepairs(UUID clientId) throws RepositoryException {
        return getClientRepairsPort.getClientRepairs(clientId);
    }

    public boolean isRepairArchive(UUID id) throws RepositoryException {
        return getRepairPort.getRepair(id).isArchive();
    }
    public Repair archivize(UUID id) throws RepositoryException {
        return deleteRepairPort.deleteRepair(id);
    }

    public Repair repair(UUID id) throws HardwareException, RepositoryException, ClientException {
        return repairPort.repair(id);
    }

    public int getPresentSize() {
        return getRepairListPort.getRepairList(false).size();
    }

    public int getArchiveSize() {
        return getRepairListPort.getRepairList(true).size();
    }

    public List<Repair> getClientsPastRepairs(UUID uuid) throws RepositoryException {
        List<Repair> pastRepairs = new ArrayList<>();
        List<Repair> repairs = getClientRepairsPort.getClientRepairs(uuid);
        for (Repair repair : repairs) {
            if (repair.isArchive()) {
                pastRepairs.add(repair);
            }
        }
        return pastRepairs;
    }

    public List<Repair> getClientsPresentRepairs(UUID uuid) throws RepositoryException {
        List<Repair> presentRepairs = new ArrayList<>();
        List<Repair> repairs = getClientRepairsPort.getClientRepairs(uuid);
        for (Repair repair : repairs) {
            if (!repair.isArchive()) {
                presentRepairs.add(repair);
            }
        }
        return presentRepairs;
    }

    public List<Repair> getAllRepairs() {
        return getAllRepairsPort.getAllRepairs();
    }

    public Repair updateRepair(UUID uuid, Repair repair) throws RepositoryException {
        return updateRepairPort.updateRepair(uuid, repair);
    }
}
