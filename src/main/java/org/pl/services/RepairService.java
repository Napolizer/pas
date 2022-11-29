package org.pl.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepairException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.DateRange;
import org.pl.model.Hardware;
import org.pl.model.Repair;
import org.pl.repositories.RepairRepository;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@ApplicationScoped
public class RepairService {
    @Inject
    private RepairRepository repairRepository;

    public Repair add(Repair repair) throws RepositoryException {
        return repairRepository.saveRepair(repair);
    }

    public Repair get(UUID id) throws RepositoryException {
        return repairRepository.getRepairById(id);
    }

    public String getInfo(UUID id) throws RepositoryException {
        return repairRepository.getRepairById(id).toString();
    }

    public List<Repair> getAllClientRepairs(UUID clientId) throws RepositoryException {
        return repairRepository.getClientRepairs(clientId);
    }

    public boolean isRepairArchive(UUID id) throws RepositoryException {
        return repairRepository.getRepairById(id).isArchive();
    }
    public Repair archivize(UUID id) throws RepositoryException {
        return repairRepository.deleteRepair(id);
    }

    public Repair repair(UUID id) throws HardwareException, RepositoryException, ClientException {
        return repairRepository.repair(id);
    }

    public int getPresentSize() {
        return repairRepository.getRepairs(false).size();
    }

    public int getArchiveSize() {
        return repairRepository.getRepairs(true).size();
    }

    public List<Repair> getClientsPastRepairs(UUID uuid) throws RepositoryException {
        List<Repair> pastRepairs = new ArrayList<>();
        List<Repair> repairs = repairRepository.getClientRepairs(uuid);
        for (Repair repair : repairs) {
            if (repair.isArchive()) {
                pastRepairs.add(repair);
            }
        }
        return pastRepairs;
    }

    public List<Repair> getClientsPresentRepairs(UUID uuid) throws RepositoryException {
        List<Repair> presentRepairs = new ArrayList<>();
        List<Repair> repairs = repairRepository.getClientRepairs(uuid);
        for (Repair repair : repairs) {
            if (!repair.isArchive()) {
                presentRepairs.add(repair);
            }
        }
        return presentRepairs;
    }

    public List<Repair> getAllRepairs() {
        return repairRepository.getAllRepairs();
    }

    public Repair updateRepair(UUID uuid, Repair repair) throws RepositoryException {
        return repairRepository.updateRepair(uuid, repair);
    }
}
