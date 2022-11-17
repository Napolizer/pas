package org.pl.services;

import org.pl.exceptions.ClientException;
import org.pl.exceptions.HardwareException;
import org.pl.exceptions.RepairException;
import org.pl.exceptions.RepositoryException;
import org.pl.model.Client;
import org.pl.model.Hardware;
import org.pl.model.Repair;
import org.pl.repositories.RepairRepository;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class RepairService {
    private final RepairRepository repairRepository;

    public RepairService(RepairRepository repairRepository) {
        this.repairRepository = repairRepository;
    }

    public Repair add(Client client, Hardware hardware) throws RepositoryException, RepairException {
        if (Objects.equals(client, null))
            throw new RepairException(RepairException.REPAIR_CLIENT_EXCEPTION);
        if (Objects.equals(hardware, null))
            throw new RepairException(RepairException.REPAIR_HARDWARE_EXCEPTION);
        return repairRepository.saveRepair(
                Repair.builder()
                        .client(client)
                        .hardware(hardware)
                        .archive(false)
                        .build());
    }

    public Repair add(Repair repair) throws RepositoryException {
        return repairRepository.saveRepair(repair);
    }

    public Repair get(UUID id) throws RepositoryException {
        return repairRepository.getRepairById(id);
    }

    public String getInfo(UUID id) throws RepositoryException {
        return repairRepository.getRepairById(id).toString();
    }

    public List<Repair> getAllClientRepairs(UUID clientId) {
        return repairRepository.getClientRepairs(clientId);
    }

    public void archive(UUID id) throws RepositoryException {
        repairRepository.deleteRepair(id);
    }

    public void repair(UUID id) throws HardwareException, RepositoryException, ClientException {
        repairRepository.repair(id);
    }

    public int getPresentSize() {
        return repairRepository.getRepairs(false).size();
    }

    public int getArchiveSize() {
        return repairRepository.getRepairs(true).size();
    }
}
